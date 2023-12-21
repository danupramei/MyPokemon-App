package com.pokemon.data.local_data.entity

import com.pokemon.shared.ui_models.UiPokemon

object PokemonEntityMapper {
    fun uiPokemonToPokemonEntity(pokemon: UiPokemon, nickName: String): PokemonEntity {
        return PokemonEntity(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            nickname = nickName,
            imageUrl = pokemon.imgUrl,
            renamedCount = 1,
            firstNickname = nickName
        )
    }
}
