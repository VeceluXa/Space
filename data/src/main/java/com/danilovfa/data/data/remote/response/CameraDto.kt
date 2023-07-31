package com.danilovfa.data.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CameraDto(
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "rover_id")
    val roverId: Int
)