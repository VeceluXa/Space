package com.danilovfa.space.presentation.mvp.map

import com.danilovfa.space.presentation.model.MapMarker
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MapPresenter(
    private val router: Router
) : MvpPresenter<MapView>() {

    private val markers = mutableListOf<MapMarker>()
    fun getMarkers() = markers.toList()

    fun onBackPressed() {
        router.exit()
    }

    fun deleteMarker(mapMarker: MapMarker): List<MapMarker> {
        markers.remove(mapMarker)
        return markers
    }

    fun addMarker(mapMarker: MapMarker) {
        markers.add(mapMarker)
    }
}