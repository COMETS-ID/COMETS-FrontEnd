package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class AssessmentResponse(

    @field:SerializedName("createdAt") val createdAt: String,

    @field:SerializedName("user_room") val userRoom: Any,

    @field:SerializedName("userRoomId") val userRoomId: Any,

    @field:SerializedName("type") val type: String,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("value") val value: String,

    @field:SerializedName("user") val user: User,
)
