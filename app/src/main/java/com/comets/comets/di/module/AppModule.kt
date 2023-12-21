package com.comets.comets.di.module

import android.content.Context
import com.comets.comets.data.prefs.AuthTokenPreferences
import com.comets.comets.data.remote.GeneralApiService
import com.comets.comets.data.remote.MoodRecognitionApiService
import com.comets.comets.data.repository.MoodRecognitionRepository
import com.comets.comets.data.repository.PsychologistRepository
import com.comets.comets.data.repository.RoomRepository
import com.comets.comets.data.repository.UserRepository
import com.comets.comets.ui.screen.login.LoginViewModel
import com.comets.comets.ui.screen.profile.ProfileViewModel
import com.comets.comets.ui.screen.pyschologist.PsychologistViewModel
import com.comets.comets.ui.screen.signup.SignupViewModel
import com.comets.comets.ui.viewmodels.camera.CameraViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthToken(@ApplicationContext context: Context): AuthTokenPreferences {
        return AuthTokenPreferences(context)
    }

    @Provides
    @Singleton
    fun provideGeneralApiService(authTokenPreferences: AuthTokenPreferences): GeneralApiService {
        return GeneralApiService.create(authTokenPreferences)
    }

    @Provides
    @Singleton
    fun provideMoodRecognitionApiService(authTokenPreferences: AuthTokenPreferences): MoodRecognitionApiService {
        return MoodRecognitionApiService.create(authTokenPreferences)
    }

    @Provides
    @Singleton
    fun provideMoodRecognitionRepository(apiService: MoodRecognitionApiService): MoodRecognitionRepository {
        return MoodRecognitionRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(generalApiService: GeneralApiService): UserRepository {
        return UserRepository(generalApiService)
    }

    @Provides
    @Singleton
    fun provideRoomRepository(generalApiService: GeneralApiService): RoomRepository {
        return RoomRepository(generalApiService)
    }

    @Singleton
    @Provides
    fun provideCameraViewModel(
    ): CameraViewModel {
        return CameraViewModel()
    }

    @Singleton
    @Provides
    fun providePsychologistViewModel(
        psychologistRepository: PsychologistRepository,
    ): PsychologistViewModel {
        return PsychologistViewModel(psychologistRepository)
    }

    @Singleton
    @Provides
    fun provideSignupViewModel(
        repository: UserRepository,
    ): SignupViewModel {
        return SignupViewModel(repository)
    }

    @Singleton
    @Provides
    fun provideLoginViewModel(
        authTokenPreferences: AuthTokenPreferences,
        repository: UserRepository,
    ): LoginViewModel {
        return LoginViewModel(
            authTokenPreferences,
            repository
        )
    }

    @Singleton
    @Provides
    fun provideProfileViewModel(
        authTokenPreferences: AuthTokenPreferences,
        repository: UserRepository,
    ): ProfileViewModel {
        return ProfileViewModel(
            authTokenPreferences,
            repository
        )
    }
}