package com.pokemon.ui.enums

import androidx.fragment.app.Fragment
import com.pokemon.ui.R

enum class RouteEnum(private val routeResId: Int) {
    LIST_POKEMON(R.string.list_pokemon_route),
    MY_POKEMON(R.string.my_pokemon_route),
    DETAIL_POKEMON(R.string.detail_pokemon_route);

    fun getRouteName(fragment: Fragment): String = fragment.getString(routeResId)
}