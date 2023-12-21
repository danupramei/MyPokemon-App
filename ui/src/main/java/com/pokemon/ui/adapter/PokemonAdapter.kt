package com.pokemon.ui.adapter

import com.pokemon.shared.base.BasePagingAdapter
import com.pokemon.shared.extension.gone
import com.pokemon.shared.extension.loadNetworkImage
import com.pokemon.shared.extension.visible
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.ui.R
import com.pokemon.ui.databinding.ItemListPokemonBinding
import javax.inject.Inject

class PokemonAdapter @Inject constructor() : BasePagingAdapter<UiPokemon, ItemListPokemonBinding>(
    ItemListPokemonBinding::inflate) {

    private var onReleaseItemButtonClicked: (Int) -> Unit = {}
    private var onRenameIconClicked: (UiPokemon) -> Unit = {}

    fun setOnReleaseItemButtonClicked(onReleaseItemClicked: (Int) -> Unit) {
        onReleaseItemButtonClicked = onReleaseItemClicked
    }

    fun setOnRenameIconClicked(onRenameClicked: (UiPokemon) -> Unit) {
        onRenameIconClicked = onRenameClicked
    }

    override fun onBindData(data: UiPokemon?, binding: ItemListPokemonBinding, position: Int) {
        binding.apply {
            data?.let { pokemon ->
                tvNamePokemon.text = pokemon.name
                ivImgPokemon.loadNetworkImage(pokemon.imgUrl, R.drawable.img_pokeball)
                pokemon.nick?.let {
                    tvNickname.text = pokemon.nick
                    groupMyPokemon.visible()
                }?: run {
                    groupMyPokemon.gone()
                }

                pokemon.id?.let { pokemonId ->
                    tvBtnRelease.setOnClickListener {
                        onReleaseItemButtonClicked.invoke(pokemonId)
                    }
                    ivEditNick.setOnClickListener {
                        onRenameIconClicked.invoke(pokemon)
                    }
                }
            }
        }
    }

}