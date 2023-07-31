package com.danilovfa.space.presentation.mvp.main

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MainPresenter(
    private val router: Router
) : MvpPresenter<MainView>() {
    fun onBackPressed() {
        router.exit()
    }
}