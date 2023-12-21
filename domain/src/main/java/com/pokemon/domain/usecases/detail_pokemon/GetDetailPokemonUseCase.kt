package com.pokemon.domain.usecases.detail_pokemon

import com.pokemon.data.repository.pokemon.PokemonRepository
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.ui_models.UiPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<UiPokemon>> {
        return flow {
            try {
                emit(Resource.Loading())
                val pokemon = pokemonRepository.getDetailPokemon(id)
                emit(Resource.Success(pokemon))
            } catch (e: Exception) {
                emit(Resource.Error(e.message, e))
            }
        }
    }
}