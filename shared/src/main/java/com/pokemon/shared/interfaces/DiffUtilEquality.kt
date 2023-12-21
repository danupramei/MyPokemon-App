package com.pokemon.shared.interfaces

import android.os.Bundle

interface DiffUtilEquality {
    fun realEquals(toCompare: Any?): Boolean
    fun payloadChange(newItem: Any?): Bundle
    override fun equals(other: Any?): Boolean
}