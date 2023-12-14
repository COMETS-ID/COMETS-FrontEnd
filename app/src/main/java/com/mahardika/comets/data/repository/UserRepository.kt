package com.mahardika.comets.data.repository

import android.util.Log
import com.mahardika.comets.data.remote.GeneralApiService
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
}