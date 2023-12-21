package com.pokemon.shared.interfaces

interface ItemListClickListener<T> {
    fun onItemClickListener(data: T?, position: Int) {}
}