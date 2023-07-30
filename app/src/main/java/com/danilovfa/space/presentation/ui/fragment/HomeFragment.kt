package com.danilovfa.space.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.space.databinding.FragmentHomeBinding
import com.danilovfa.space.presentation.mvp.home.HomePresenter
import com.danilovfa.space.presentation.mvp.home.HomeView
import com.danilovfa.space.utils.BackButtonListener
import dagger.hilt.android.AndroidEntryPoint
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeView,
    BackButtonListener {

    @Inject
    lateinit var hiltPresenter: HomePresenter

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter() = hiltPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            presenter.navigateToPhotoScreen("URL Test")
        }
        presenter.getPhotos()
    }

    override fun showPhotos(photos: List<MarsRoverPhoto>) {
        binding.textView.text = "${photos.size} photos."
    }

    override fun showError(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                presenter.getPhotos()
            }
            .show()
    }

    override fun showError(messageRes: Int) {

    }

    override fun hideProgressBar() {

    }

    override fun showProgressBar() {
        binding.textView.text = "LOADING..."
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }
}