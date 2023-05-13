package com.devyash.errorandeventhandling.repositories

import com.devyash.errorandeventhandling.api.ApiService
import com.devyash.errorandeventhandling.models.posts
import retrofit2.Response

class PostRepository {
   private val apiService=ApiService

    suspend fun getPosts():Response<posts>{
        return apiService.postsApi.getPosts()
    }
}