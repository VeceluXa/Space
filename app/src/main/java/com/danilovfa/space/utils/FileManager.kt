package com.danilovfa.space.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileManager(private val context: Context) {
    fun saveTempImageBitmap(image: Bitmap): Uri {
        val cacheDir = context.cacheDir


        val imageFile = File(cacheDir, SHARED_IMAGE_FILENAME)
        return try {
            val outputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            FileProvider.getUriForFile(
                context,
                FILE_PROVIDER_AUTHORITY,
                imageFile
            )
        } catch (e: IOException) {
            Uri.EMPTY
        }
    }

    companion object {
        const val SHARED_IMAGE_FILENAME = "shared_image.jpg"
        const val FILE_PROVIDER_AUTHORITY = "com.danilovfa.space.FileProvider"
    }
}