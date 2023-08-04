package com.danilovfa.space.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar

const val TAG_MAX_LENGTH = 23

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= TAG_MAX_LENGTH) tag else tag.substring(0 until TAG_MAX_LENGTH)
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

 fun Drawable.toBitmapDescriptor(): BitmapDescriptor {
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    canvas.setBitmap(bitmap)
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}