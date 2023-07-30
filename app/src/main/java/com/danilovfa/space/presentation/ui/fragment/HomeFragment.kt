package com.danilovfa.space.presentation.ui.fragment

import com.danilovfa.space.databinding.FragmentHomeBinding
import com.danilovfa.space.utils.BackButtonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    BackButtonListener {
    override fun onBackPressed(): Boolean {
        return true
    }
}