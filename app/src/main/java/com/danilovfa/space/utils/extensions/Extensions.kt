package com.danilovfa.space.utils.extensions

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0..22)
    }

fun Int.toDp(context: Context): Float = this / context.resources.displayMetrics.density

fun Float.toPx(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics).toInt()
}

fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar
        .make(this, messageRes, length)
        .show()
}