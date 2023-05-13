package com.devyash.errorandeventhandling.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    val postsApi:PostAndPassengerApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postsApi=retrofit.create(PostAndPassengerApi::class.java)
    }
}