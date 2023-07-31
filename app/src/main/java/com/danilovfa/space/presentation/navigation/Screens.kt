package com.danilovfa.space.presentation.navigation

import com.danilovfa.space.presentation.ui.fragment.HomeFragment
import com.danilovfa.space.presentation.ui.fragment.MapFragment
import com.danilovfa.space.presentation.ui.fragment.PhotoFragment
import com.danilovfa.space.presentation.ui.fragment.TabContainerFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun HomeScreen() = FragmentScreen {
        HomeFragment()
    }

    fun MapScreen() = FragmentScreen {
        MapFragment()
    }

    fun PhotoScreen(imageUrl: String) = FragmentScreen {
        PhotoFragment.getNewInstance(imageUrl)
    }

    fun TabContainer(containerId: String) = FragmentScreen {
        TabContainerFragment.getNewInstance(containerId)
    }
}