package com.pokemon.data.service

import com.pokemon.data.repository.models.PagingListResponse
import com.pokemon.data.repository.models.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonServices {

    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Response<PagingListResponse>

    @GET("pokemon/{id}/")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): Response<Pokemon>

}