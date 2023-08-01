package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import com.danilovfa.space.databinding.FragmentMapBinding
import com.danilovfa.space.presentation.mvp.map.MapPresenter
import com.danilovfa.space.presentation.mvp.map.MapView
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.utils.extensions.addGradient
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
        binding.itemPhoto.showImageTextView.addGradient(requireContext())

        setupToolbar()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            setupToolbar()
    }

    private fun setupToolbar() {
        hideAppBar()
        toolbarHideBackButton()
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }
}