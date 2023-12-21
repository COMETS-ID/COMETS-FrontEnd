package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(

    @field:SerializedName("comment") val comment: List<CommentItem>,

    @field:SerializedName("posting") val posting: Posting,
)

data class Posting(

    @field:SerializedName("createdAt") val createdAt: String,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("posting") val posting: String,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("userId") val userId: Int,

    @field:SerializedName("user") val user: User,

    @field:SerializedName("updatedAt") val updatedAt: String,
)

data class CommentItem(

    @field:SerializedName("comment") val comment: String,

    @field:SerializedName("id") val id: Int,

    @field:SerializedName("uuid") val uuid: String,

    @field:SerializedName("user") val user: User,
)
