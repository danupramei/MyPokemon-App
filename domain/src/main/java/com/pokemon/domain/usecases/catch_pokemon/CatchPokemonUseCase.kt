package com.pokemon.domain.usecases.catch_pokemon

import com.pokemon.data.repository.my_pokemon.MyPokemonRepository
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.ui_models.UiPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatchPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {


    suspend operator fun invoke(pokemon: UiPokemon, nickName: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            try {
                myPokemonRepository.addPokemon(pokemon, nickName)
                emit(Resource.Success(true))
            } catch (e: Exception){
                emit(Resource.Error("Gagal mendapatkan pokemon"))
            }
        }
    }
}