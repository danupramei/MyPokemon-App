package com.pokemon.domain.usecases.list_mypokemon

import com.pokemon.data.repository.my_pokemon.MyPokemonRepository
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.ui_models.UiPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {
    operator fun invoke(): Flow<Resource<List<UiPokemon>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val listMyPokemon = myPokemonRepository.getAllPokemon()
                emit(Resource.Success(listMyPokemon))
            } catch (e: Exception) {
                emit(
                    Resource.Error("Failed to get my pokemon")
                )
            }
        }
    }
}