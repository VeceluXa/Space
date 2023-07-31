package com.danilovfa.data.data.remote

import com.danilovfa.data.BuildConfig
import com.danilovfa.data.data.remote.response.MarsRoverPhotoListDto
import com.danilovfa.data.utils.Constants.Companion.DEFAULT_ROVER
import com.danilovfa.data.utils.Constants.Companion.DEFAULT_SOL
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {
    /**
     * @param rover One of 3 rovers: curiosity, opportunity and spirit
     * @param sol Martian day. Counts from the day rover was landed
     * @param camera One of rover camera (see https://api.nasa.gov/ for more information)
     */
    @GET("rovers/{rover}/photos")
    fun getRoversPhotos(
        @Path("rover") rover: String = DEFAULT_ROVER,
        @Query("sol") sol: Int = DEFAULT_SOL,
        @Query("camera") camera: String? = null,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): Single<MarsRoverPhotoListDto>
}