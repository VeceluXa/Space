package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentPhotoBinding
import com.danilovfa.space.presentation.mvp.photo.PhotoPresenter
import com.danilovfa.space.presentation.mvp.photo.PhotoView
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.presentation.ui.dialog.TutorialDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate), PhotoView,
    BackButtonListener, MenuProvider {

    @Inject
    lateinit var hiltPresenter: PhotoPresenter

    @InjectPresenter
    lateinit var presenter: PhotoPresenter

    @ProvidePresenter
    fun providePresenter() = hiltPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupToolbar()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            setupToolbar()
    }

    private fun setupToolbar() {
        showAppBar()

        toolbarShowBackButton {
            onBackPressed()
        }
    }

    override fun showTutorial() {
        TutorialDialogFragment.display(childFragmentManager)
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL"
        fun getNewInstance(imageUrl: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_IMAGE_URL, imageUrl)
                }
            }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_details, menu)
        val menuShare = menu.findItem(R.id.menuShare)
        menuShare.actionView?.setOnClickListener {

        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}