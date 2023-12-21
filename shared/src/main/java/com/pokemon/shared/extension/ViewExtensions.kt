package com.pokemon.shared.extension

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.pokemon.shared.utils.SafeClickListener

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setSafeOnClickListener(
    lifecycle: Lifecycle,
    onSafeClick: (View?) -> Unit
) {
    val safeClickListener = SafeClickListener(lifecycle) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun ImageView.loadNetworkImage(imageUrl: String?, defaultImageResourceId: Int? = null) {
    imageUrl?.let {
        Glide.with(this)
            .load(it)
            .error(defaultImageResourceId)
            .into(this)
    }
}