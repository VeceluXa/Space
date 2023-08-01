package com.danilovfa.space.presentation.mvp.home

import androidx.annotation.StringRes
import com.danilovfa.common.domain.model.MarsRoverPhoto
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface HomeView : MvpView {
    fun showPhotos(photos: List<MarsRoverPhoto>, scrollPosition: Int)
    fun showError(message: String)
    fun showError(@StringRes messageRes: Int)
    fun hideProgressBar()
    fun showProgressBar()
}