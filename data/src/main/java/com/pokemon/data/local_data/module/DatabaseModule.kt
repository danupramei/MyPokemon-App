package com.pokemon.data.local_data.module

import android.content.Context
import androidx.room.Room
import com.pokemon.data.local_data.dao.PokemonDao
import com.pokemon.data.local_data.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext context: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(
        db: PokemonDatabase
    ): PokemonDao = db.pokemonDao()
}