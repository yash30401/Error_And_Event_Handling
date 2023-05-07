package com.devyash.errorandeventhandling.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object PostService {
    val postsApi:PostsApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postsApi=retrofit.create(PostsApi::class.java)
    }
}