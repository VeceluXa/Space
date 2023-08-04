package com.danilovfa.space.presentation.mvp.photo

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PhotoView : MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showTutorial()
}