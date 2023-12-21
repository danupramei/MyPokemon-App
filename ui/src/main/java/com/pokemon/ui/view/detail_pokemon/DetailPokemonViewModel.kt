package com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.domain.commons.Resource
import com.pokemon.domain.usecases.catch_pokemon.CatchPokemonUseCase
import com.pokemon.domain.usecases.check_is_mypokemon.CheckIsMyPokemonUseCase
import com.pokemon.domain.usecases.detail_pokemon.GetDetailPokemonUseCase
import com.pokemon.shared.extension.mutableSharedFlow
import com.pokemon.shared.extension.mutableStateFlow
import com.pokemon.shared.ui_models.UiPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val checkIsMyPokemonUseCase: CheckIsMyPokemonUseCase
): ViewModel() {
    private val _pokemonState = mutableStateFlow<Resource<UiPokemon>>(Resource.Init())
    val pokemonState get() = _pokemonState.asStateFlow()

    private val _isAdded = mutableSharedFlow<Resource<Boolean>>()
    val isAdded get() = _isAdded.asSharedFlow()

    private val _isMyPokemon = mutableSharedFlow<Boolean>()
    val isMyPokemon get() = _isMyPokemon.asSharedFlow()

    fun getPokemon(id: Int) {
        viewModelScope.launch {
            getDetailPokemonUseCase(id).collectLatest {
                _pokemonState.emit(it)
            }
        }
    }

    fun catchPokemon(pokemon: UiPokemon, nick: String) {
        viewModelScope.launch {
            catchPokemonUseCase(pokemon, nick).collectLatest {
                _isAdded.emit(it)
            }
        }
    }

    fun checkIsMyPokemon(id: Int) {
        viewModelScope.launch {
            checkIsMyPokemonUseCase(id).collectLatest {
                _isMyPokemon.emit(it)
            }
        }
    }
}