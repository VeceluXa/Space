package com.danilovfa.data.data.repository

import com.danilovfa.common.domain.repository.SharedPrefsRepository
import com.danilovfa.data.data.local.SharedPrefsManager
import com.danilovfa.data.utils.Constants.Companion.HAS_TUTORIAL_BEEN_SHOWN_PREFERENCE_KEY

class SharedPrefsRepositoryImpl(
    private val sharedPrefsManager: SharedPrefsManager,
) : SharedPrefsRepository {
    override fun hasTutorialBeenShown(): Boolean {
        return sharedPrefsManager.getFirstTimeTrue(HAS_TUTORIAL_BEEN_SHOWN_PREFERENCE_KEY)
    }
}