package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class GetPostResponse(

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("posting") val posting: String,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("user") val user: User,
)

data class User(

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("email") val email: String,

    @field:SerializedName("username") val username: String,
)
