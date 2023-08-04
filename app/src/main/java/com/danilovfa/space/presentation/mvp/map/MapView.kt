package com.danilovfa.space.presentation.mvp.map

import com.danilovfa.space.presentation.model.MapMarker
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MapView : MvpView {
    fun showMarkers(markers: List<MapMarker>)
}