package com.danilovfa.data.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarsRoverPhotoListDto(
    @Json(name = "photos")
    val photos: List<PhotoDto>
)