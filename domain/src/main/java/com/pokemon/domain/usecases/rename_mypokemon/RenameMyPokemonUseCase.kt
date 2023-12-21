package com.pokemon.domain.usecases.rename_mypokemon

import com.pokemon.data.repository.my_pokemon.MyPokemonRepository
import com.pokemon.domain.commons.Resource
import com.pokemon.shared.utils.MyPokemonUtil.getRenamedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RenameMyPokemonUseCase @Inject constructor(
    private val myPokemonRepository: MyPokemonRepository
) {

    operator fun invoke(id: Int, newNick: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                val itemToUpdate = myPokemonRepository.getPokemonById(id)
                itemToUpdate?.let {
                    val newName = getRenamedName(
                        newNick,
                        itemToUpdate.renamedCount
                    )
                    itemToUpdate.nickname = newName
                    itemToUpdate.firstNickname = newNick
                    itemToUpdate.renamedCount++

                    myPokemonRepository.updatePokemon(itemToUpdate)

                    emit(Resource.Success(true))
                }?: run {
                    emit(Resource.Error("Pokemon tidak ditemukan"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Gagal mengganti nama"))
            }
        }
    }
}