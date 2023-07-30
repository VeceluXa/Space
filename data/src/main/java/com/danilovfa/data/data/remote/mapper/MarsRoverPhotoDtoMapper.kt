package com.danilovfa.data.data.remote.mapper

import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.utils.Mapper
import com.danilovfa.data.data.remote.response.PhotoDto

class MarsRoverPhotoDtoMapper: Mapper<PhotoDto, MarsRoverPhoto> {
    override fun mapFromEntity(entity: PhotoDto): MarsRoverPhoto {
        return MarsRoverPhoto(
            rover = entity.rover.name,
            camera = entity.camera.name,
            photoUrl = entity.imgSrc
        )
    }

    override fun mapToEntity(domain: MarsRoverPhoto): PhotoDto {
        TODO("Not yet implemented")
    }
}