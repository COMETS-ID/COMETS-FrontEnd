package com.mahardika.comets.data.repository

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String?) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
}
