package com.pokemon.data.repository.my_pokemon

import com.pokemon.data.local_data.entity.PokemonEntity
import com.pokemon.shared.ui_models.UiPokemon
import javax.inject.Inject

class MyPokemonRepository @Inject constructor(
    private val myPokemonDataSource: MyPokemonDataSource
) {
    suspend fun addPokemon(pokemon: UiPokemon, nickName: String) {
        return myPokemonDataSource.addPokemon(pokemon, nickName)
    }

    suspend fun updatePokemon(pokemonEntity: PokemonEntity) {
        return myPokemonDataSource.updatePokemon(pokemonEntity)
    }

    suspend fun getAllPokemon(): List<UiPokemon> {
        return myPokemonDataSource.getAllPokemon()
    }

    suspend fun getPokemonById(id: Int): PokemonEntity? {
        return myPokemonDataSource.getPokemonById(id)
    }

    suspend fun deletePokemonById(id: Int): Boolean {
        return myPokemonDataSource.deletePokemonById(id)
    }
}