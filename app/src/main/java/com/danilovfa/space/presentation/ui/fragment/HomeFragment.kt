package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.window.layout.WindowMetricsCalculator
import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.utils.Constants.Companion.ROVER_CURIOSITY
import com.danilovfa.common.utils.Constants.Companion.ROVER_OPPORTUNITY
import com.danilovfa.common.utils.Constants.Companion.ROVER_SPIRIT
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentHomeBinding
import com.danilovfa.space.presentation.mvp.home.HomePresenter
import com.danilovfa.space.presentation.mvp.home.HomeView
import com.danilovfa.space.presentation.ui.adapter.PhotosAdapter
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.presentation.ui.dialog.RadioDialogFragment
import com.danilovfa.space.presentation.ui.dialog.TextDialogFragment
import com.danilovfa.space.utils.extensions.toDp
import dagger.hilt.android.AndroidEntryPoint
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeView,
    BackButtonListener, PhotosAdapter.OnItemClickListener, MenuProvider {

    @Inject
    lateinit var hiltPresenter: HomePresenter

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter() = hiltPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupToolbar()

        presenter.getPhotos()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            setupToolbar()
    }

    private fun setupToolbar() {
        showAppBar()
        toolbarHideBackButton()
    }

    private fun getNumberOfColumns(): Int {
        val windowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())
        val currentBounds = windowMetrics.bounds
        val widthDp = currentBounds.width().toDp(requireContext())

        val numberOfColumns = (widthDp / ITEM_WIDTH_DP).toInt()
        return numberOfColumns
    }

    override fun showPhotos(photos: List<MarsRoverPhoto>, scrollPosition: Int) {
        val photosAdapter = PhotosAdapter(photos)
        val gridLayoutManager = GridLayoutManager(requireContext(), getNumberOfColumns())
        gridLayoutManager.scrollToPosition(scrollPosition)

        photosAdapter.setOnItemClickListener(this)

        binding.photosRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = photosAdapter
        }
    }

    override fun showError(message: String) {
        TextDialogFragment.display(
            fragmentManager = childFragmentManager,
            title = getString(R.string.error_title),
            body = message
        )
    }

    override fun showError(messageRes: Int) {
        TextDialogFragment.display(
            fragmentManager = childFragmentManager,
            title = getString(R.string.error_title),
            body = getString(messageRes)
        )
    }

    override fun hideProgressBar() {
        binding.apply {
            photosProgressBar.visibility = View.GONE
            photosRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun showProgressBar() {
        binding.apply {
            photosProgressBar.visibility = View.VISIBLE
            photosRecyclerView.visibility = View.GONE
        }
    }

    private fun selectRover() {
        val rovers = mapOf(
            getString(R.string.rover_curiosity) to ROVER_CURIOSITY,
            getString(R.string.rover_opportunity) to ROVER_OPPORTUNITY,
            getString(R.string.rover_spirit) to ROVER_SPIRIT
        )

        RadioDialogFragment.display(
            fragmentManager = childFragmentManager,
            title = getString(R.string.select_rover),
            radioButtons = rovers.keys.toList()
        ) { position ->
            rovers[position]?.let {
                presenter.selectRover(it)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    override fun onItemClick(photo: MarsRoverPhoto) {
        saveScrollPosition()
        presenter.navigateToPhotoScreen(photo.photoUrl)
    }

    private fun saveScrollPosition() {
        val layoutManager = binding.photosRecyclerView.layoutManager as GridLayoutManager
        val scrollPosition = layoutManager.findFirstVisibleItemPosition()
        presenter.saveScrollPosition(scrollPosition)
    }

    companion object {
        const val ITEM_WIDTH_DP = 164
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_home, menu)

        val menuSelectRover = menu.findItem(R.id.menuSelectRover)
        menuSelectRover.actionView?.setOnClickListener {
            selectRover()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}