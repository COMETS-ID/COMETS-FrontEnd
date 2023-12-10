package com.mahardika.comets.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("user")
    suspend fun getUser()
}