package com.comets.comets.data.response

import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @field:SerializedName("msg") val msg: String,
)