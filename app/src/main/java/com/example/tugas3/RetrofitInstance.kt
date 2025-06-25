package com.example.tugas3

import kotlin.lazy
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"

    val api: ApiService by lazy{
        Retrofit.Builder().
        baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build().
        create(ApiService::class.java)
    }
}