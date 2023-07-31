package com.danilovfa.space.presentation.mvp.photo

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class PhotoPresenter(
    private val router: Router
) : MvpPresenter<PhotoView>() {
    fun onBackPressed() {
        router.exit()
    }
}