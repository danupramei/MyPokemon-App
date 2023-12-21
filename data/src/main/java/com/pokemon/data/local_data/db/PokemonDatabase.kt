package com.pokemon.data.local_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pokemon.data.local_data.dao.PokemonDao
import com.pokemon.data.local_data.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}