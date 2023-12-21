package com.pokemon.data.repository.models

import android.os.Parcelable
import com.pokemon.shared.ui_models.UiPokemon
import com.pokemon.shared.ui_models.UiStat
import com.pokemon.shared.utils.MyPokemonUtil.getImageUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val abilities: List<Ability>?,
    val base_experience: Int?,
    val forms: List<Form>?,
    val game_indices: List<GameIndice>?,
    val height: Int?,
    val id: Int?,
    val is_default: Boolean?,
    val location_area_encounters: String?,
    val moves: List<Move>?,
    val name: String?,
    val url: String?,
    val order: Int?,
    val species: Species?,
    val sprites: Sprites?,
    val stats: List<Stat>?,
    val types: List<Type>?,
    val weight: Int?
) : Parcelable {
    fun toUiPokemonFromList(): UiPokemon {
        val idPokemon = this.url?.split("/")?.dropLast(1)?.last()?.toInt()
        return UiPokemon(
            id = idPokemon,
            name = this.name,
            nick = null,
            weight = null,
            height = null,
            imgUrl = getImageUrl(idPokemon),
            type = this.types.toString(),
            moves = listOf(),
            stats = listOf()

        )
    }

    fun toUiPokemonFromDetail(): UiPokemon {
        return UiPokemon(
            id = this.id,
            name = this.name,
            nick = null,
            weight = "${weight}Kg",
            height = "$height m",
            imgUrl = this.sprites?.other?.home?.front_default,
            type = this.types?.map { it.type?.name }?.joinToString(separator = "/"),
            moves = this.moves?.map {
                it.move?.name
            },
            stats = this.stats?.map {
                UiStat(
                    name = it.stat?.name,
                    base_stat = it.base_stat
                )
            }
        )
    }
}

@Parcelize
data class Ability(
    val ability: AbilityX?,
    val is_hidden: Boolean?,
    val slot: Int?
) : Parcelable

@Parcelize
data class Form(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class GameIndice(
    val game_index: Int?,
    val version: Version?
) : Parcelable

@Parcelize
data class Move(
    val move: MoveX?,
    val version_group_details: List<VersionGroupDetail>?
) : Parcelable

@Parcelize
data class Species(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class Sprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?,
    val other: Other?
) : Parcelable

@Parcelize
data class Other(
    val dream_world: DreamWorld?,
    val home: Home?
) : Parcelable

@Parcelize
data class Stat(
    val base_stat: Int?,
    val effort: Int?,
    val stat: StatX?
) : Parcelable

@Parcelize
data class Type(
    val slot: Int?,
    val type: TypeX?
) : Parcelable

@Parcelize
data class AbilityX(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class Version(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class MoveX(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class VersionGroupDetail(
    val level_learned_at: Int?,
    val move_learn_method: MoveLearnMethod?,
    val version_group: VersionGroup?
) : Parcelable

@Parcelize
data class MoveLearnMethod(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class VersionGroup(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class DreamWorld(
    val front_default: String?
) : Parcelable

@Parcelize
data class Home(
    val front_default: String?,
    val front_shiny: String?,
) : Parcelable

@Parcelize
data class StatX(
    val name: String?,
    val url: String?
) : Parcelable

@Parcelize
data class TypeX(
    val name: String?,
    val url: String?
) : Parcelable