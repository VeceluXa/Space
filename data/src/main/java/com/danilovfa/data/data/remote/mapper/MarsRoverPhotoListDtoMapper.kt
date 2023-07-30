package com.danilovfa.data.data.remote.mapper

import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.utils.Mapper
import com.danilovfa.data.data.remote.response.MarsRoverPhotoListDto

class MarsRoverPhotoListDtoMapper: Mapper<MarsRoverPhotoListDto, List<MarsRoverPhoto>> {
    private val photoMapper = MarsRoverPhotoDtoMapper()

    override fun mapFromEntity(entity: MarsRoverPhotoListDto): List<MarsRoverPhoto> {
        return entity.photos.map { photoDto ->
            photoMapper.mapFromEntity(photoDto)
        }
    }

    override fun mapToEntity(domain: List<MarsRoverPhoto>): MarsRoverPhotoListDto {
        TODO("Not yet implemented")
    }

}