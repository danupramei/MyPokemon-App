package com.pokemon.domain.usecases.list_pokemon

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pokemon.data.repository.pokemon.PokemonRepository
import com.pokemon.shared.constant.Const.DEFAULT_MAX_SIZE_PAGE
import com.pokemon.shared.ui_models.UiPokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke() : Flow<PagingData<UiPokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_MAX_SIZE_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                pokemonRepository.getListPokemon(DEFAULT_MAX_SIZE_PAGE)
            }
        ).flow
    }
}