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
    private var isFragmentInTabVisible = true
    private var doesMenuProviderExist = false
    private var isFragmentHidden: Boolean? = null

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

        savedInstanceState?.getBoolean(IS_FRAGMENT_HIDDEN)?.let {
            isFragmentHidden = it
        }

        setupToolbar()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentHidden = hidden
        if (!hidden && isVisible) {
            setupToolbar()
        } else {
            toolbarClear()
            toolbarHide()
        }
    }

    protected open fun setupToolbar() {
        if (isFragmentHidden == false || (isFragmentHidden != true && isVisible)) {
            addMenuProvider()
            toolbarShow()
        }
    }

    private fun addMenuProvider() {
        if (!doesMenuProviderExist) {
            doesMenuProviderExist = true
            val menuHost = requireActivity()
            menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    private fun removeMenuProvider() {
        doesMenuProviderExist = false
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

    protected fun toolbarHideBackButton() {
        activity?.apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setHomeAsUpIndicator(null)
            }
            binding.materialToolbar.setNavigationOnClickListener(null)
        }
    }

    override fun onStart() {
        super.onStart()
        isFragmentInTabVisible = true
    }

    override fun onStop() {
        super.onStop()
        toolbarClear()
        removeMenuProvider()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isFragmentHidden?.let {
            outState.putBoolean(IS_FRAGMENT_HIDDEN, it)
        }
        outState.putBoolean(DOES_MENU_PROVIDER_EXIST_ID, doesMenuProviderExist)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) = Unit
    override fun onMenuItemSelected(menuItem: MenuItem) = false

    companion object {
        private const val IS_FRAGMENT_HIDDEN = "isFragmentHidden"
        private const val DOES_MENU_PROVIDER_EXIST_ID = "Does menu provider exist"
    }
}