package com.danilovfa.space.presentation.model

import com.google.android.gms.maps.model.Marker

data class MapMarker(
    val label: String,
    val marker: Marker
)