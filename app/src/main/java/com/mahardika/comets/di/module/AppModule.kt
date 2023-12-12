package com.mahardika.comets.di.module

import com.mahardika.comets.data.remote.ApiService
import com.mahardika.comets.data.remote.MoodRecognitionApiService
import com.mahardika.comets.data.repository.MoodRecognitionRepository
import com.mahardika.comets.data.repository.UserRepository
import com.mahardika.comets.ui.viewmodels.camera.CameraViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl("https://api.example.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewApiService(): MoodRecognitionApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl("https://new-api.example.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoodRecognitionApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoodRecognitionRepository(apiService: MoodRecognitionApiService): MoodRecognitionRepository {
        return MoodRecognitionRepository(apiService)
    }

    @Provides
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideCameraViewModel(
        moodRecognitionRepository: MoodRecognitionRepository
    ): CameraViewModel {
        return CameraViewModel(moodRecognitionRepository)
    }
}