package com.danilovfa.space.presentation.mvp.map

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MapPresenter(
    private val router: Router
) : MvpPresenter<MapView>() {
    fun onBackPressed() {
        router.exit()
    }
}