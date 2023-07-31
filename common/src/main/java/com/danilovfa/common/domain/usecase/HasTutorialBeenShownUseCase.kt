package com.danilovfa.common.domain.usecase

import com.danilovfa.common.domain.repository.SharedPrefsRepository
import javax.inject.Inject

class HasTutorialBeenShownUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
) {
    fun execute(): Boolean {
        return sharedPrefsRepository.hasTutorialBeenShown()
    }
}