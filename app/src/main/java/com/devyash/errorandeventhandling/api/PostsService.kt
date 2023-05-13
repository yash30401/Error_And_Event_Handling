package com.devyash.errorandeventhandling.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostsService {
     val postsApi:PostsApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postsApi=retrofit.create(PostsApi::class.java)
    }
}