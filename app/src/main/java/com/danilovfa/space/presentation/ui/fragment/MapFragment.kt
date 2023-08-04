package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentMapBinding
import com.danilovfa.space.presentation.model.MapMarker
import com.danilovfa.space.presentation.mvp.map.MapPresenter
import com.danilovfa.space.presentation.mvp.map.MapView
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.presentation.ui.adapter.MarkersAdapter
import com.danilovfa.space.presentation.ui.dialog.RadioDialogFragment
import com.danilovfa.space.presentation.ui.dialog.TypeDialogFragment
import com.danilovfa.space.utils.extensions.toBitmapDescriptor
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import dagger.hilt.android.AndroidEntryPoint
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate), MapView,
    BackButtonListener {

    @Inject
    lateinit var hiltPresenter: MapPresenter

    @InjectPresenter
    lateinit var presenter: MapPresenter

    @ProvidePresenter
    fun providePresenter() = hiltPresenter

    private var googleMap: GoogleMap? = null
    private var markersAdapter: MarkersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetDialog()

        setupMap(savedInstanceState)
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbarHideBackButton()
    }

    private fun setupBottomSheetDialog() {
        val behavior = BottomSheetBehavior.from(binding.markersBottomSheetLayout).apply {
            isHideable = false
        }

        val callBack = object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> setupMarkersRecyclerView()
                    BottomSheetBehavior.STATE_EXPANDED -> setupMarkersRecyclerView()
                    BottomSheetBehavior.STATE_COLLAPSED -> resetMarkersRecyclerView()
                    else -> Unit
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        }

        behavior.addBottomSheetCallback(callBack)
    }

    private fun setupMarkersRecyclerView() {
        if (markersAdapter != null) return

        markersAdapter = MarkersAdapter()
        updateMarkersAdapter()

        markersAdapter?.setOnMarkerDeleteListener(object : MarkersAdapter.OnMarkerDeleteListener {
            override fun onMarkerDelete(marker: MapMarker) {
                deleteMarker(marker)
            }
        })

        val linearLayoutManager = LinearLayoutManager(requireContext())

        binding.markersRecyclerView.apply {
            adapter = markersAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun deleteMarker(marker: MapMarker) {
        presenter.deleteMarker(marker)
        marker.marker.remove()
        updateMarkersAdapter()
    }

    private fun updateMarkersAdapter() {
        markersAdapter?.differ?.submitList(presenter.getMarkers())
    }

    private fun resetMarkersRecyclerView() {
        markersAdapter = null
    }

    private fun setupMap(savedInstanceState: Bundle?) {

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        binding.mapView.apply {
            onCreate(mapViewBundle)
            getMapAsync { map ->
                googleMap = map
                addSavedMarkers()
                map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark))
                setOnMapClick()
            }
        }
    }

    private fun addSavedMarkers() {
        val savedMarkers = presenter.getMarkers().takeIf { it.isNotEmpty() } ?: return
        savedMarkers.forEach { presenter.deleteMarker(it) }

        val iconDrawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.icon_map_marker) ?: return

        val newMarkers = mutableListOf<MapMarker>()

        savedMarkers.forEach { marker ->
            val markerOptions = MarkerOptions()
                .position(marker.marker.position)
                .icon(iconDrawable.toBitmapDescriptor())

            val newMarker = googleMap?.addMarker(markerOptions) ?: return
            newMarkers.add(MapMarker(marker.label, newMarker))
        }

        newMarkers.forEach { presenter.addMarker(it) }
        updateMarkersAdapter()
    }

    private fun setOnMapClick() {
        googleMap?.setOnMapClickListener { location ->
            TypeDialogFragment.display(
                childFragmentManager,
                hint = getString(R.string.marker_name)
            ) { label ->
                addMarker(label, location)
            }
        }
    }

    private fun addMarker(label: String, location: LatLng) {
        val iconDrawable =
            AppCompatResources.getDrawable(requireContext(), R.drawable.icon_map_marker) ?: return

        val markerOptions = MarkerOptions()
            .position(location)
            .icon(iconDrawable.toBitmapDescriptor())

        val marker = googleMap?.addMarker(markerOptions) ?: return

        presenter.addMarker(MapMarker(label, marker))
        updateMarkersAdapter()
    }

    private fun changeMapViewType() {
        val types = mapOf(
            MAP_TYPE_DEFAULT to GoogleMap.MAP_TYPE_NORMAL,
            MAP_TYPE_HYBRID to GoogleMap.MAP_TYPE_HYBRID,
            MAP_TYPE_SATELLITE to GoogleMap.MAP_TYPE_SATELLITE
        )

        val selectedMapType = when (googleMap?.mapType) {
            GoogleMap.MAP_TYPE_HYBRID -> MAP_TYPE_HYBRID
            GoogleMap.MAP_TYPE_SATELLITE -> MAP_TYPE_SATELLITE
            else -> MAP_TYPE_DEFAULT
        }

        RadioDialogFragment.display(
            fragmentManager = childFragmentManager,
            title = getString(R.string.change_map_view),
            selectedItem = selectedMapType,
            radioButtons = types.keys.toList()
        ) { selectedStyle ->
            types[selectedStyle]?.let { mapType ->
                googleMap?.mapType = mapType
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        binding.mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onStart() {
        binding.mapView.onStart()
        super.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_map, menu)

        val menuChangeMapView = menu.findItem(R.id.menuChangeMapView)
        menuChangeMapView.actionView?.setOnClickListener {
            changeMapViewType()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    companion object {
        const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"

        const val MAP_TYPE_DEFAULT = "Default"
        const val MAP_TYPE_HYBRID = "Hybrid"
        const val MAP_TYPE_SATELLITE = "Satellite"
    }

    override fun showMarkers(markers: List<MapMarker>) {

    }
}