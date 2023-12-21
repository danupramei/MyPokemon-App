package com.pokemon.data.network

data class ErrorResponse(
    val error: String? = null,
    val error_description: String? = null,
    val responseData: ErrorResponseData? = null
)

data class ErrorResponseData(
    val responseMsg: String? = null,
    val timestamp: String? = null,
    val status: String? = null,
    val exception: String? = null,
    val message: String? = null,
    val path: String? = null,
    val error: String? = null,
    val responseCode: Int? = null,
)


