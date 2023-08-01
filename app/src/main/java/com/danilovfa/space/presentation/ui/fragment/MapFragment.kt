package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentMapBinding
import com.danilovfa.space.presentation.mvp.map.MapPresenter
import com.danilovfa.space.presentation.mvp.map.MapView
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.utils.extensions.snack
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun setupToolbar() {
        super.setupToolbar()
        toolbarHideBackButton()
    }

    private fun changeMapView() {
        binding.root.snack(R.string.change_map_view)
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_map, menu)

        val menuChangeMapView = menu.findItem(R.id.menuChangeMapView)
        menuChangeMapView.actionView?.setOnClickListener {
            changeMapView()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}