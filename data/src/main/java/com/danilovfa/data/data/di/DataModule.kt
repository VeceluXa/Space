package com.danilovfa.data.data.di

import android.content.Context
import com.danilovfa.common.domain.repository.RoverPhotosRemoteRepository
import com.danilovfa.common.domain.repository.SharedPrefsRepository
import com.danilovfa.data.data.local.SharedPrefsManager
import com.danilovfa.data.data.remote.NasaApi
import com.danilovfa.data.data.repository.RoverPhotosRemoteRepositoryImpl
import com.danilovfa.data.data.repository.SharedPrefsRepositoryImpl
import com.danilovfa.data.utils.Constants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideSharedPrefsManager(
        @ApplicationContext context: Context
    ): SharedPrefsManager {
        return SharedPrefsManager(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefsRepository(sharedPrefsManager: SharedPrefsManager): SharedPrefsRepository {
        return SharedPrefsRepositoryImpl(sharedPrefsManager)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val loggingInterceptorClient =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(loggingInterceptorClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMarsRoverApi(retrofit: Retrofit): NasaApi {
        return retrofit.create(NasaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoverPhotoRemoteRepository(api: NasaApi): RoverPhotosRemoteRepository {
        return RoverPhotosRemoteRepositoryImpl(api)
    }
}