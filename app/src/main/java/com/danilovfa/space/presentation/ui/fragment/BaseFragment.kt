package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.danilovfa.space.R
import com.danilovfa.space.presentation.ui.MainActivity
import moxy.MvpAppCompatFragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : MvpAppCompatFragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private var activity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity() is MainActivity) {
            activity = requireActivity() as MainActivity
        }
        setup()
    }

    private fun setup() {
        showBottomNavigation()
        hideAppBar()
    }

    protected fun hideBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.GONE
    }

    protected fun showBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.VISIBLE
    }

    protected fun hideAppBar() {
        val appBar = activity?.binding?.appBarLayout
        appBar?.visibility = View.GONE
    }

    protected fun showAppBar() {
        val appBar = activity?.binding?.appBarLayout
        appBar?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun toolbarShowBackButton(onNavigateUp: () -> Unit) {
        activity?.apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.toolbar_icon_back)
            }
            binding.materialToolbar.setNavigationOnClickListener {
                onNavigateUp()
            }
        }
    }

    protected fun toolbarHideBackButton() {
        activity?.apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setHomeAsUpIndicator(null)
            }
            binding.materialToolbar.setNavigationOnClickListener(null)
        }
    }
}