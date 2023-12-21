package com.kimiafarma.marvee.feature_onboarding.ui.fragments.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {
    private val _delayState = MutableSharedFlow<Boolean>()
    val delayState get() = _delayState.asSharedFlow()

    fun setDelayScreen(timeInSeconds: Int) {
        viewModelScope.launch {
            _delayState.emit(true)
            delay((timeInSeconds * 1000).toLong())
            _delayState.emit(false)
        }
    }
}