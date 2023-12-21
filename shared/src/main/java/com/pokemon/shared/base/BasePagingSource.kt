package com.pokemon.shared.base

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T: Any> : PagingSource<Int, T>() {
    val errorDefaultMessage = "Terjadi Kesalahan"

    fun isSuccessful(code: Int?): Boolean {
        return code in 200..299
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}