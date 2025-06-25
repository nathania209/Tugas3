package com.example.tugas3

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): UserResponse

    @GET("products")
    suspend fun getProducts(): ProductResponse
}
