package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class RoomDetailUserResponse(

    @field:SerializedName("userRoom") val userRoom: List<UserRoomItem>,

    @field:SerializedName("room") val room: Room,
)

data class UserRoomItem(

    @field:SerializedName("val") val value: Int,

    @field:SerializedName("roleRoom") val roleRoom: String,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("userId") val userId: Int,

    @field:SerializedName("user") val user: User,

    @field:SerializedName("roomId") val roomId: Int,
)
