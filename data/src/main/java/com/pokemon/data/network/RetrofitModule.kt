package com.pokemon.data.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.pokemon.data.constant.NetworkConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @ApplicationContext context: Context,
        httpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return httpClientBuilder.apply {
            addInterceptor(ChuckerInterceptor(context))
        }.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named(NetworkConst.HiltNamed.HEADER_INTERCEPTOR) headerInterceptor: Interceptor,
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(NetworkConst.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(NetworkConst.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .connectTimeout(NetworkConst.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named(NetworkConst.HiltNamed.HEADER_INTERCEPTOR)
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(NetworkConst.HEADER_ACCEPT, NetworkConst.HEADER_APP_JSON)
            .addHeader(NetworkConst.HEADER_CONTENT_TYPE, NetworkConst.HEADER_APP_JSON)
            .build()
        chain.proceed(request)
    }

}