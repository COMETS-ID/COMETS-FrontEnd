package com.mahardika.comets.data.remote

import retrofit2.http.GET

interface GeneralApiService {

    @GET("user")
    suspend fun getUser()
}