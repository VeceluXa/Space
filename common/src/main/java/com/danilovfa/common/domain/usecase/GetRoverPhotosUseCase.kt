package com.danilovfa.common.domain.usecase

import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.domain.repository.RoverPhotosRemoteRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetRoverPhotosUseCase @Inject constructor(
    private val remoteRepository: RoverPhotosRemoteRepository
) {
    fun execute(): Single<List<MarsRoverPhoto>> {
        return remoteRepository.fetchRoverPhotos()
    }
}