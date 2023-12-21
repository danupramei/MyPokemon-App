package com.pokemon.data.repository.models

data class PagingListResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>?
)