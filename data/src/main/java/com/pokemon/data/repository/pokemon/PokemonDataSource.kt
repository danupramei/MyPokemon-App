package com.pokemon.data.repository.pokemon

import com.pokemon.data.network.validateResponse
import com.pokemon.data.service.PokemonServices
import com.pokemon.shared.base.BasePagingSource
import com.pokemon.shared.ui_models.UiPokemon
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val pokemonServices: PokemonServices
) {
    fun getPokemonListPaging(
        limit: Int
    ): BasePagingSource<UiPokemon> {
        return object : BasePagingSource<UiPokemon>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPokemon> {
                val pageIndex = params.key ?: 0
                return try {
                    val response = pokemonServices.getListPokemon(limit, pageIndex)
                    if (response.isSuccessful) {
                        val data = response.body()
                        val list = data?.results
                        list?.let { listPokemon ->
                            LoadResult.Page(
                                data = listPokemon.map { it.toUiPokemonFromList() },
                                prevKey = if (data.previous == null) null else pageIndex - limit,
                                nextKey = if (data.next == null) null else pageIndex + limit
                            )
                        }?: kotlin.run {
                            LoadResult.Error(Exception(errorDefaultMessage))
                        }
                    } else {
                        LoadResult.Error(Exception(errorDefaultMessage))
                    }
                } catch (e: IOException) {
                    LoadResult.Error(e)
                } catch (e: HttpException) {
                    LoadResult.Error(e)
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }
    }

    suspend fun getDetailPokemon(id: Int): UiPokemon? {
        var result: UiPokemon? = null
        pokemonServices.getDetailPokemon(id).validateResponse { data ->
            result = data.toUiPokemonFromDetail()
        }
        return result
    }
}