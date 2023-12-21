package com.comets.comets.data.repository

import com.comets.comets.data.remote.MoodRecognitionApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class MoodRecognitionRepository @Inject constructor(
    private val apiService: MoodRecognitionApiService,
) {
    suspend fun postFaceCapture(image: MultipartBody.Part): Resource<String> {
        return try {
            val result = apiService.postFaceCapture(
                image,
            )

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }

    }
}
