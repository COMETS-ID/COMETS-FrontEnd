package com.mahardika.comets.data.remote

import com.mahardika.comets.data.response.FaceRecognitionPostResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MoodRecognitionApiService {

    @Multipart
    @POST
    fun postFaceCapture(@Part image: MultipartBody.Part): FaceRecognitionPostResponse
}