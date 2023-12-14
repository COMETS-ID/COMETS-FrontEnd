package com.mahardika.comets.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object Utils {
    fun createMultipartBody(
        uri: Uri,
        multipartName: String,
    ): MultipartBody.Part {
        val documentImage = decodeFile(uri.path!!)
        val file = File(uri.path!!)
        val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
        documentImage.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            os
        )
        os.close()
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(
            name = multipartName,
            file.name,
            requestBody
        )
    }

    private fun decodeFile(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(
            filePath,
            options
        )

        options.inSampleSize = calculateInSampleSize(
            options,
            512,
            512
        )

        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(
            filePath,
            options
        )
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int,
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}