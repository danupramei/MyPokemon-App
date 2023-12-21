package com.pokemon.data.repository.pokemon

import com.pokemon.shared.base.BasePagingSource
import com.pokemon.shared.ui_models.UiPokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) {
    fun getListPokemon(limit: Int): BasePagingSource<UiPokemon> {
        return pokemonDataSource.getPokemonListPaging(limit)
    }

    suspend fun getDetailPokemon(id: Int): UiPokemon? {
        return pokemonDataSource.getDetailPokemon(id)
    }
}