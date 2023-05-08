package com.devyash.errorandeventhandling.repositories

import com.devyash.errorandeventhandling.api.PostService
import com.devyash.errorandeventhandling.models.posts
import retrofit2.Response

class PostRepository {
   private val postService=PostService

    suspend fun getPosts():Response<posts>{
        return postService.postsApi.getPosts()
    }
}