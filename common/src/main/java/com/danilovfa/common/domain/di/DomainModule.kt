package com.danilovfa.common.domain.di

import com.danilovfa.common.domain.repository.RoverPhotosRemoteRepository
import com.danilovfa.common.domain.repository.SharedPrefsRepository
import com.danilovfa.common.domain.usecase.GetRoverPhotosUseCase
import com.danilovfa.common.domain.usecase.HasTutorialBeenShownUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetRoverPhotosUseCase(
        remoteRepository: RoverPhotosRemoteRepository
    ): GetRoverPhotosUseCase {
        return GetRoverPhotosUseCase(remoteRepository)
    }

    @Provides
    fun provideHasTutorialBeenShownUseCase(
        sharedPrefsRepository: SharedPrefsRepository
    ): HasTutorialBeenShownUseCase {
        return HasTutorialBeenShownUseCase(sharedPrefsRepository)
    }
}