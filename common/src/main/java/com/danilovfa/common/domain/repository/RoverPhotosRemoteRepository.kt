package com.danilovfa.common.domain.repository

import com.danilovfa.common.domain.model.MarsRoverPhoto
import io.reactivex.rxjava3.core.Single

interface RoverPhotosRemoteRepository {
    fun fetchRoverPhotos(rover: String): Single<List<MarsRoverPhoto>>
}