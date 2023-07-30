package com.danilovfa.space.presentation.di

import com.danilovfa.common.domain.usecase.GetRoverPhotosUseCase
import com.danilovfa.space.presentation.navigation.LocalNavigatorHolder
import com.danilovfa.space.presentation.mvp.home.HomePresenter
import com.danilovfa.space.presentation.mvp.main.MainPresenter
import com.danilovfa.space.presentation.mvp.map.MapPresenter
import com.danilovfa.space.presentation.mvp.photo.PhotoPresenter
import com.danilovfa.space.utils.Constants.Companion.HOME_TAB_ID
import com.danilovfa.space.utils.Constants.Companion.MAP_TAB_ID
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AppModule {
    @Provides
    fun provideMainPresenter(router: Router): MainPresenter {
        return MainPresenter(router)
    }

    @Provides
    fun provideHomePresenter(
        localNavigatorHolder: LocalNavigatorHolder,
        getRoverPhotosUseCase: GetRoverPhotosUseCase,
    ): HomePresenter {
        val router = localNavigatorHolder.getCicerone(HOME_TAB_ID).router
        return HomePresenter(
            getRoverPhotosUseCase = getRoverPhotosUseCase,
            router = router
        )
    }

    @Provides
    fun providePhotoPresenter(localNavigatorHolder: LocalNavigatorHolder): PhotoPresenter {
        val router = localNavigatorHolder.getCicerone(HOME_TAB_ID).router
        return PhotoPresenter(router)
    }

    @Provides
    fun provideMapPresenter(localNavigatorHolder: LocalNavigatorHolder): MapPresenter {
        val router = localNavigatorHolder.getCicerone(MAP_TAB_ID).router
        return MapPresenter(router)
    }




}