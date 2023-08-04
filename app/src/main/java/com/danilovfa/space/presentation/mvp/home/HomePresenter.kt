package com.danilovfa.space.presentation.mvp.home

import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.common.domain.usecase.GetRoverPhotosUseCase
import com.danilovfa.common.utils.Constants.Companion.ROVER_CURIOSITY
import com.danilovfa.space.R
import com.danilovfa.space.presentation.navigation.Screens.PhotoScreen
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class HomePresenter @Inject constructor(
    private val router: Router, private val getRoverPhotosUseCase: GetRoverPhotosUseCase
) : MvpPresenter<HomeView>() {

    private var photos = listOf<MarsRoverPhoto>()
    private var scrollPosition = 0

    private var photosDisposable: Disposable? = null

    var rover: String = ROVER_CURIOSITY

    fun getPhotos() {
        viewState.showProgressBar()
        if (photos.isEmpty()) {
            photosDisposable = getRoverPhotosUseCase.execute(rover)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newPhotos ->
                    updatePhotos(newPhotos)
                }, { error ->
                    updateError(error.message)
                })
        } else {
            viewState.apply {
                hideProgressBar()
                showPhotos(photos, scrollPosition)
            }
        }
    }

    fun saveScrollPosition(position: Int) {
        scrollPosition = position
    }

    private fun updatePhotos(newPhotos: List<MarsRoverPhoto>) {
        photosDisposable?.dispose()
        photos = newPhotos
        viewState.apply {
            hideProgressBar()
            showPhotos(photos, scrollPosition)
        }
    }

    private fun updateError(errorMessage: String?) {
        photosDisposable?.dispose()
        viewState.apply {
            hideProgressBar()
            if (errorMessage != null)
                showError(errorMessage)
            else
                showError(R.string.error_default)
        }

    }

    fun selectRover(newRover: String) {
        photos = listOf()
        scrollPosition = 0
        rover = newRover
        getPhotos()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun navigateToPhotoScreen(imageUrl: String) {
        router.navigateTo(PhotoScreen(imageUrl))
    }
}