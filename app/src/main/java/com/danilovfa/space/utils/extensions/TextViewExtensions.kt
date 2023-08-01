package com.danilovfa.space.utils.extensions

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import com.danilovfa.space.R

fun TextView.addGradient(context: Context) {
    val width = paint.measureText(text.toString())
    val shader = LinearGradient(0f, 0f, width, textSize, intArrayOf(
        context.getColor(R.color.pink_1),
        context.getColor(R.color.pink_2)
    ), null, Shader.TileMode.REPEAT)
    paint.shader = shader
}