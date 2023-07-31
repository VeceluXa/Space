package com.danilovfa.space.presentation.mvp.home

import androidx.annotation.StringRes
import com.danilovfa.common.domain.model.MarsRoverPhoto
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface HomeView: MvpView {
    fun showPhotos(photos: List<MarsRoverPhoto>)
    fun showError(message: String)
    fun showError(@StringRes messageRes: Int)
    fun hideProgressBar()
    fun showProgressBar()
}