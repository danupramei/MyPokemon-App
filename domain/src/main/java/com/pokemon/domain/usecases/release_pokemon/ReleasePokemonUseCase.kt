package com.pokemon.domain.usecases.release_pokemon

import com.pokemon.data.repository.my_pokemon.MyPokemonRepository
import com.pokemon.domain.commons.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReleasePokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(pokemonId: Int): Flow<Resource<Boolean>> {
        return flow {
            try {
                if (myPokemonRepository.deletePokemonById(pokemonId))
                    emit(Resource.Success(true))
                else
                    emit(Resource.Error("Gagal melepas pokemon"))
            }catch (e: Exception){
                emit(Resource.Error("Gagal melepas pokemon"))
            }
        }
    }
}