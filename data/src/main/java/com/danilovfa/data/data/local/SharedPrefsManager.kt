package com.danilovfa.data.data.local

import android.content.Context
import com.danilovfa.data.utils.Constants.Companion.PREFERENCE_FILE_KEY
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    context: Context
) {
    private val sharedPref = context.getSharedPreferences(
        PREFERENCE_FILE_KEY,
        Context.MODE_PRIVATE
    )

    fun getFirstTimeTrue(name: String): Boolean {
        return if (!sharedPref.contains(name)) {
            val edit = sharedPref.edit()
            edit.putBoolean(name, false)
            edit.apply()
            true
        } else {
            sharedPref.getBoolean(name, false)
        }
    }
}