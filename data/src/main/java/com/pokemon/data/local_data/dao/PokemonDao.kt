package com.pokemon.data.local_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pokemon.data.local_data.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemon(pokemonEntity: PokemonEntity)

    @Update
    suspend fun updatePokemon(pokemonEntity: PokemonEntity)

    @Query("DELETE FROM my_pokemon WHERE pokemonId=:id")
    suspend fun deletePokemonById(id: Int)

    @Query("SELECT * FROM my_pokemon")
    suspend fun getAllMyPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM my_pokemon WHERE pokemonId=:id")
    suspend fun getMyPokemonById(id: Int): PokemonEntity?
}