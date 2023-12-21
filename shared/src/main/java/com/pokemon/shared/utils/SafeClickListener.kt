package com.pokemon.shared.utils

import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SafeClickListener(
    lifecycle: Lifecycle,
    private val onSafeCLick: (View?) -> Unit
) : View.OnClickListener {
    private var onClicked = false
    private val debouncePeriod = 1500L
    private val coroutineScope = lifecycle.coroutineScope
    private var onClickListener: Job? = null

    override fun onClick(v: View?) {
        onClickListener?.cancel()
        Log.d("SAFE CLICK", "- RETURN $onClicked")

        onClickListener = coroutineScope.launch {
            if (!onClicked) {
                onClicked = true
                onSafeCLick(v)
                Log.d("SAFE CLICK", "- ACTION is RUNNING")
            }
            delay(debouncePeriod)
            onClicked = false
            onClickListener?.cancel()
        }
    }
}
