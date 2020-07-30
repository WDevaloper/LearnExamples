package com.github.pokemon.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        return getRetrofit().create(clazz)
    }
}