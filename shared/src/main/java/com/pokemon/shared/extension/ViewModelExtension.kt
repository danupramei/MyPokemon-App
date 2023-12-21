package com.pokemon.shared.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> mutableSharedFlow(
    replay: Int = 0,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
): MutableSharedFlow<T> {
    return MutableSharedFlow(replay = replay, onBufferOverflow = onBufferOverflow)
}

fun <T> mutableStateFlow(defaultValue: T? = null): MutableStateFlow<T?> {
    return MutableStateFlow(defaultValue)
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}