package com.pokemon.shared.ui_models

import android.os.Bundle
import android.os.Parcelable
import com.pokemon.shared.interfaces.DiffUtilEquality
import kotlinx.parcelize.Parcelize
import java.util.Objects

@Parcelize
data class UiPokemon(
    val id: Int?,
    val name: String?,
    val nick: String?,
    val weight: String?,
    val height: String?,
    val imgUrl: String?,
    val type: String?,
    val moves: List<String?>?,
    val stats: List<UiStat?>?
): Parcelable, DiffUtilEquality {

    override fun realEquals(toCompare: Any?): Boolean {
        if (this === toCompare) return true
        if (toCompare == null || javaClass != toCompare.javaClass) return false
        val item: UiPokemon = toCompare as UiPokemon
        return this.id == item.id
                && this.name == item.name
                && this.nick == item.nick
                && this.imgUrl == item.imgUrl
    }

    override fun payloadChange(newItem: Any?): Bundle {
        val bundle = Bundle()
        newItem as UiPokemon
        bundle.putParcelable("newItem", newItem)
        return bundle
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UiPokemon
        if (this.id != other.id) return false
        return true
    }
}

@Parcelize
data class UiStat(
    val name: String?,
    val base_stat: Int?
): Parcelable