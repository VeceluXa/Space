package com.danilovfa.space.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun Context.loadImageByUrl(imageUrl: String, view: ImageView, @DrawableRes placeholder: Int) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeholder)
        .fitCenter()
        .into(view)
}

fun Context.loadImageByUrlCenterCropWithPlaceholder(
    imageUrl: String,
    view: ImageView,
    @DrawableRes placeholder: Int
) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeholder)
        .centerCrop()
        .into(view)
}

fun Context.loadImageToBitmap(imageUrl: String, onImageLoaded: (image: Bitmap) -> Unit) {
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onImageLoaded(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}