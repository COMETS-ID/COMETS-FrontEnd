package com.comets.comets.data.remote

import com.comets.comets.data.prefs.AuthTokenPreferences
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface MoodRecognitionApiService {

    @Multipart
    @POST("predict")
    suspend fun postFaceCapture(
        @Part file: MultipartBody.Part,
    ): String

    companion object {
        private const val BASE_URL = "https://predict-okxscopjda-uc.a.run.app/"

        fun create(authTokenPreferences: AuthTokenPreferences): MoodRecognitionApiService {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val client = OkHttpClient
                .Builder()
                .addInterceptor(AuthInterceptor(authTokenPreferences))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(
                    60,
                    TimeUnit.SECONDS
                )
                .readTimeout(
                    60,
                    TimeUnit.SECONDS
                )
                .writeTimeout(
                    60,
                    TimeUnit.SECONDS
                )
                .build()

            return Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(MoodRecognitionApiService::class.java)
        }
    }
}

