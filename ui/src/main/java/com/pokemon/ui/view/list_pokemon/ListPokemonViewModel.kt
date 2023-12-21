package com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pokemon.domain.usecases.list_pokemon.GetListPokemonUseCase
import com.pokemon.shared.extension.mutableStateFlow
import com.pokemon.shared.ui_models.UiPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val getListPokemonUseCase: GetListPokemonUseCase
): ViewModel() {
    private val _listPokemon = mutableStateFlow<PagingData<UiPokemon>?>()
    val listPokemon get() = _listPokemon.asStateFlow()

    fun getListPokemon() {
        viewModelScope.launch {
            getListPokemonUseCase()
                .cachedIn(this)
                .collectLatest {
                    _listPokemon.emit(it)
                }
        }
    }
}