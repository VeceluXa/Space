package com.danilovfa.space.presentation.ui.fragment

import com.danilovfa.space.databinding.FragmentMapBinding
import com.danilovfa.space.utils.BackButtonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    BackButtonListener {
    override fun onBackPressed(): Boolean {
        return true
    }
}