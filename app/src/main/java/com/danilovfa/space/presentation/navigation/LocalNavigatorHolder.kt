package com.danilovfa.space.presentation.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class LocalNavigatorHolder {
    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerId: String): Cicerone<Router> =
        containers.getOrPut(containerId) {
            Cicerone.create()
        }
}