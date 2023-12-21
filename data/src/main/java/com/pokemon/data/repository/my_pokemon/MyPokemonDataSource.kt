package com.pokemon.data.repository.my_pokemon

import com.pokemon.data.local_data.dao.PokemonDao
import com.pokemon.data.local_data.entity.PokemonEntity
import com.pokemon.data.local_data.entity.PokemonEntityMapper
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.shared.utils.MyPokemonUtil
import javax.inject.Inject

class MyPokemonDataSource @Inject constructor(
    private val pokemonDao: PokemonDao
) {
    suspend fun addPokemon(pokemon: UiPokemon, nickName: String) {
        return pokemonDao.addPokemon(
            PokemonEntityMapper.uiPokemonToPokemonEntity(
                pokemon,
                nickName
            )
        )
    }

    suspend fun updatePokemon(pokemonEntity: PokemonEntity) {
        return pokemonDao.updatePokemon(pokemonEntity)
    }

    suspend fun getAllPokemon(): List<UiPokemon> {
        return pokemonDao.getAllMyPokemon().map { it.toUiPokemon() }
    }

    suspend fun getPokemonById(id: Int): PokemonEntity? {
        return pokemonDao.getMyPokemonById(id)
    }

    suspend fun deletePokemonById(id: Int): Boolean {
        return if (MyPokemonUtil.isPrime()){
            pokemonDao.deletePokemonById(id)
            true
        } else {
            false
        }
    }
}