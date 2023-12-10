package com.mahardika.comets.data.repository

import android.util.Log
import com.mahardika.comets.data.remote.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUser() {
        try {
            Log.d("UserRepository", "getUser()")
            apiService.getUser()
        } catch (e: Exception) {
            Log.e("UserRepository", "getUser() error")
        }
    }
}