package com.danilovfa.space.presentation.ui.fragment

import com.danilovfa.space.databinding.FragmentMapBinding
import com.danilovfa.space.presentation.mvp.map.MapPresenter
import com.danilovfa.space.presentation.mvp.map.MapView
import com.danilovfa.space.utils.BackButtonListener
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


    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }
}