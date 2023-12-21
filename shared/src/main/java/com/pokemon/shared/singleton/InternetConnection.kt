package com.pokemon.shared.singleton

import androidx.lifecycle.MutableLiveData
import com.pokemon.shared.extension.asLiveData

object InternetConnection {

    private val mIsInternetConnected = MutableLiveData(true)
    val isInternetConnected get() = mIsInternetConnected.asLiveData()
}