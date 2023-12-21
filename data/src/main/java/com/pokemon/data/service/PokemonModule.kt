package com.pokemon.data.service

import com.pokemon.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PokemonModule {
    @Provides
    fun providePokemonService(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): PokemonServices {
        return retrofitBuilder.client(okHttpClient).baseUrl(BuildConfig.BASE_URL).build()
            .create(PokemonServices::class.java)
    }
}