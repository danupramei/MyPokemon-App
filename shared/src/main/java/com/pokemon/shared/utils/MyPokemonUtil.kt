package com.pokemon.shared.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlin.random.Random

object MyPokemonUtil {

    fun isCatched(): Boolean {
        val random = Random.nextDouble()
        return random < 0.5
    }

    fun isPrime(): Boolean {
        val n = Random.nextInt(from = 1, until = 100)
        if (n <= 1) {
            return false
        }

        for (i in 2 until n) {
            if (n % i == 0) {
                return false
            }
        }
        return true
    }

    fun getRenamedName(firstName: String, count: Int): String {
        var fib1 = 0
        var fib2 = 1
        var renamedCount = 0

        for (i in 1..count) {
            renamedCount = fib1
            fib1 = fib2
            fib2 = renamedCount + fib1
        }

        return "$firstName-$renamedCount"
    }

    fun getImageUrl(id: Int?): String{
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"
    }

    fun imageUrlToBitmap(context: Context, imageUrl: String): Bitmap? {
        return try {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .submit()
                .get()

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}