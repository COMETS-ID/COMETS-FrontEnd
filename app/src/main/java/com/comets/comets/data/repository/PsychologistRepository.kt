package com.comets.comets.data.repository

import android.content.Context
import com.comets.comets.data.remote.GeneralApiService
import com.comets.comets.data.response.PsychologistResponseItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class PsychologistRepository @Inject constructor(private val apiService: GeneralApiService) {
    fun getPsychologistsFromLocal(context: Context): Resource<List<PsychologistResponseItem>> {
        val json = context.assets
            .open("psychologist.json")
            .bufferedReader()
            .use { it.readText() }
        return Resource.Success(
            Gson().fromJson(
                json,
                object : TypeToken<List<PsychologistResponseItem>>() {}.type
            )
        )
    }

    fun getPsychologistByIdFromLocal(
        context: Context,
        id: Int,
    ): Resource<PsychologistResponseItem> {
        val json = context.assets
            .open("psychologist.json")
            .bufferedReader()
            .use { it.readText() }
        val psychologistList: List<PsychologistResponseItem> = Gson().fromJson(
            json,
            object : TypeToken<List<PsychologistResponseItem>>() {}.type
        )

        val matchingPsychologist = psychologistList.find { it.id == id }

        return matchingPsychologist?.let { Resource.Success(it) }
            ?: Resource.Error("Psychologist not found with id: $id")
    }

}