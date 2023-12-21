package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class AssessmentFromUserRoomResponse(

    @field:SerializedName("user") val user: User,

    @field:SerializedName("room") val room: Room,

    @field:SerializedName("assesment") val assesment: List<AssesmentItem>,
)

data class AssesmentItem(

    @field:SerializedName("createdAt") val createdAt: String,

    @field:SerializedName("userRoomId") val userRoomId: Int,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("type") val type: String,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("value") val value: String,

    @field:SerializedName("updatedAt") val updatedAt: String,
)
