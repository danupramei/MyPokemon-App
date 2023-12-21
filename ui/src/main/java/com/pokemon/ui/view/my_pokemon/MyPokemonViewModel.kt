package com.pokemon.ui.view.my_pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.domain.commons.Resource
import com.pokemon.domain.usecases.list_mypokemon.GetMyPokemonUseCase
import com.pokemon.domain.usecases.release_pokemon.ReleasePokemonUseCase
import com.pokemon.domain.usecases.rename_mypokemon.RenameMyPokemonUseCase
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
class MyPokemonViewModel @Inject constructor(
    private val getMyPokemonUseCase: GetMyPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val renameMyPokemonUseCase: RenameMyPokemonUseCase
): ViewModel() {

    private val _listMyPokemon = mutableStateFlow<Resource<List<UiPokemon>>>()
    val listMyPokemon get() = _listMyPokemon.asStateFlow()

    private val _error = mutableSharedFlow<String?>()
    val error = _error.asSharedFlow()

    fun getMyPokemon() {
        viewModelScope.launch {
            getMyPokemonUseCase()
                .collectLatest {
                    _listMyPokemon.emit(it)
                }
        }
    }

    fun releasePokemon(id: Int) {
        viewModelScope.launch {
            releasePokemonUseCase(id).collectLatest {
                when (it){
                    is Resource.Success -> {
                        if (it.data == true) getMyPokemon()
                    }
                    is Resource.Error -> _error.emit(it.message)
                    else ->{}
                }
            }
        }
    }

    fun renamePokemon(id: Int, nick: String) {
        viewModelScope.launch {
            renameMyPokemonUseCase(id, nick).collectLatest {
                when (it){
                    is Resource.Success -> getMyPokemon()
                    is Resource.Error -> _error.emit(it.message)
                    else ->{}
                }
            }
        }
    }
}