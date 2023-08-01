package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentHomeBinding
import com.danilovfa.space.presentation.mvp.home.HomePresenter
import com.danilovfa.space.presentation.mvp.home.HomeView
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.presentation.ui.dialog.TextDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeView,
    BackButtonListener, MenuProvider {

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


    override fun showPhotos(photos: List<MarsRoverPhoto>, scrollPosition: Int) {

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

    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
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