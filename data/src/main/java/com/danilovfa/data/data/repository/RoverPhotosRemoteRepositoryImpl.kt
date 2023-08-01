package com.danilovfa.data.data.repository

import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.domain.repository.RoverPhotosRemoteRepository
import com.danilovfa.data.data.remote.NasaApi
import com.danilovfa.data.data.remote.mapper.MarsRoverPhotoListDtoMapper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RoverPhotosRemoteRepositoryImpl @Inject constructor(
    private val api: NasaApi
) : RoverPhotosRemoteRepository {
    private val mapper = MarsRoverPhotoListDtoMapper()

    override fun fetchRoverPhotos(rover: String): Single<List<MarsRoverPhoto>> {
        return api.getRoversPhotos(rover)
            .subscribeOn(Schedulers.io())
            .map { listEntity ->
                val domain = mapper.mapFromEntity(listEntity)
                domain
            }
    }
}