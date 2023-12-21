package com.comets.comets.data.repository

import android.util.Log
import com.comets.comets.data.remote.GeneralApiService
import com.comets.comets.data.response.AssessmentResponse
import com.comets.comets.data.response.BasicResponse
import com.comets.comets.data.response.LoginResponse
import com.comets.comets.ui.screen.login.LoginRequest
import com.comets.comets.ui.screen.signup.SignupRequest
import com.comets.comets.ui.viewmodels.assessment.PostAssessmentRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private val generalApiService: GeneralApiService) {
    suspend fun getUser() {
        try {
            Log.d(
                "UserRepository",
                "getUser()"
            )
            generalApiService.getUser()
        } catch (e: Exception) {
            Log.e(
                "UserRepository",
                "getUser() error"
            )
        }
    }

    suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse> {
        return try {
            val result = generalApiService.loginUser(loginRequest)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun signupUser(signupRequest: SignupRequest): Resource<BasicResponse> {
        return try {
            val result = generalApiService.signupUser(signupRequest)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun logoutUser(): Resource<Unit> {
        return try {
            generalApiService.logoutUser()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getMe(): Resource<BasicResponse> {
        return try {
            val result = generalApiService.getMe()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getUserAssessments(): Resource<List<AssessmentResponse>> {
        return try {
            val result = generalApiService.getUserAssessments()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun postAssessment(postAssessmentRequest: PostAssessmentRequest): Resource<BasicResponse> {
        Log.d(
            "postassessment",
            "repoclicked"
        )
        return try {
            val result = generalApiService.postAssessment(postAssessmentRequest)
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun postAssessmentFromUserRoom(
        uuid: String,
        postAssessmentRequest: PostAssessmentRequest,
    ): Resource<BasicResponse> {
        return try {
            val result = generalApiService.postAssesmentFromUserRoom(
                uuid,
                postAssessmentRequest
            )

            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }
}