package com.pokemon.domain.commons

sealed class Resource<T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val message: String?, val exception: Throwable? = null) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Init<T> : Resource<T>()
}