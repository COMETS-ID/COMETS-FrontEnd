package com.mahardika.comets.data.response

import com.google.gson.annotations.SerializedName

data class PsychologistResponse(

    @field:SerializedName("PsychologistsResponse") val psychologistsResponse: List<PsychologistResponseItem?>? = null,
)

data class PsychologistResponseItem(

    @field:SerializedName("image") val image: Any? = null,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("specialization") val specialization: String,

    @field:SerializedName("tariff") val tariff: String,

    @field:SerializedName("description") val description: String? = null,

    @field:SerializedName("id") val id: Int,
)
