package com.danilovfa.space.presentation.mvp.photo

import android.util.Log
import com.danilovfa.common.domain.usecase.HasTutorialBeenShownUseCase
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class PhotoPresenter(
    private val router: Router,
    private val hasTutorialBeenShownUseCase: HasTutorialBeenShownUseCase
) : MvpPresenter<PhotoView>() {

    fun doShowTutorial() {
        val doShow = hasTutorialBeenShownUseCase.execute()
        Log.d("MySharedPrefs", "doShowTutorial: $doShow")
        if (doShow) {
            Log.d("MySharedPrefs", "doShowTutorial: doShow")
            viewState.showTutorial()
        }
    }

    fun onBackPressed() {
        router.exit()
    }
}