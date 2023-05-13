package com.devyash.errorandeventhandling.repositories

import com.devyash.errorandeventhandling.api.PostsService
import com.devyash.errorandeventhandling.models.posts
import retrofit2.Response

class PostRepository {
   private val apiService=PostsService

    suspend fun getPosts():Response<posts>{
        return apiService.postsApi.getPosts()
    }
}