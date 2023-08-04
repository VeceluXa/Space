package com.danilovfa.data.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "earth_date")
    val earthDate: String,
    @Json(name = "sol")
    val sol: Int,
    @Json(name = "camera")
    val camera: CameraDto,
    @Json(name = "rover")
    val rover: RoverDto,
    @Json(name = "img_src")
    val imgSrc: String
)