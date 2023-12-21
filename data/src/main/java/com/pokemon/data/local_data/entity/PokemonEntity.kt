package com.pokemon.data.local_data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemon.shared.ui_models.UiPokemon

@Entity("my_pokemon")
data class PokemonEntity(
    @PrimaryKey()
    @ColumnInfo(name = "pokemonId")
    val pokemonId: Int?,
    @ColumnInfo(name= "pokemonName")
    val pokemonName: String?,
    @ColumnInfo(name = "firstNickname")
    var firstNickname: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "renamedCount")
    var renamedCount: Int
) {
    fun toUiPokemon(): UiPokemon {
        return UiPokemon(
            id = this.pokemonId,
            name = this.pokemonName,
            nick = this.nickname,
            imgUrl = this.imageUrl,
            weight = null,
            height = null,
            type = null,
            moves = listOf(),
            stats = listOf()
        )
    }
}