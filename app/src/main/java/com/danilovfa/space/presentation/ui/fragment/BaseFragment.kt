package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.danilovfa.space.R
import com.danilovfa.space.presentation.ui.MainActivity
import moxy.MvpAppCompatFragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : MvpAppCompatFragment(), MenuProvider {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private var activity: MainActivity? = null
    private var isFragmentInTabVisible = false

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

        showBottomNavigation()
        addMenuProvider()
        setupToolbar()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            setupToolbar()
        } else {
            toolbarClear()
            toolbarHide()
        }
    }

    protected open fun setupToolbar() {
        if (isFragmentInTabVisible) {
            addMenuProvider()
        }
        toolbarShow()
    }

    private fun addMenuProvider() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun removeMenuProvider() {
        val menuHost = requireActivity()
        menuHost.removeMenuProvider(this)
    }

    protected fun hideBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.GONE
    }

    protected fun showBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.VISIBLE
    }

    protected fun toolbarClear() {
        if (isFragmentInTabVisible) {
            removeMenuProvider()
        }
        activity?.binding?.materialToolbar?.menu?.clear()
    }

    protected fun toolbarHide() {
        val appBar = activity?.binding?.appBarLayout
        appBar?.visibility = View.GONE
    }

    protected fun toolbarShow() {
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

    override fun onStart() {
        super.onStart()
        isFragmentInTabVisible = true
    }

    override fun onStop() {
        super.onStop()
        isFragmentInTabVisible = false
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

    open override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    open override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}