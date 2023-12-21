package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class RoomResponse(

    @field:SerializedName("val") val value: Int,

    @field:SerializedName("roleRoom") val roleRoom: String,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("room") val room: Room,
)

data class Room(

    @field:SerializedName("owner") val owner: Int,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("user") val user: User,

    @field:SerializedName("capacity") val capacity: Int,
)

