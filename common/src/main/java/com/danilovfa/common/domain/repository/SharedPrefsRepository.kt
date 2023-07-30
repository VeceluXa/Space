package com.danilovfa.common.domain.repository

interface SharedPrefsRepository {
    fun hasTutorialBeenShown(): Boolean
}