package com.pokemon.data.network

import com.google.gson.Gson
import retrofit2.Response


suspend fun <T> Response<T>.validateResponse(
    onSuccess: suspend (data: T) -> Unit
) {

    if (this.isSuccessful) {
        val body = this.body()
        body?.let { onSuccess(it) }
    } else {
        val errorMessage = this.getErrorMessage()
        throw Exception(errorMessage)
    }
}

fun <T> Response<T>.getErrorMessage(): String {
    val errorResponse = this.getErrorResponse()
    val message = errorResponse?.error_description ?: kotlin.run {
        errorResponse?.responseData?.message ?: errorResponse?.error
        ?: getDefaultErrorMessage()
    }


    return message

}

fun <T> Response<T>.getErrorResponse(): ErrorResponse? {
    return Gson().fromJson(
        this.errorBody()?.string(),
        ErrorResponse::class.java
    )

}

fun getDefaultErrorMessage(): String {
    return "Server error. Coba lagi nanti!"
}
