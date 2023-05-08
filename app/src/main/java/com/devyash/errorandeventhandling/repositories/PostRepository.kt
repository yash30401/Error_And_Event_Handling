package com.devyash.errorandeventhandling.repositories

import com.devyash.errorandeventhandling.api.PostService
import com.devyash.errorandeventhandling.models.posts
import com.devyash.errorandeventhandling.other.NetworkResult
import retrofit2.Response

class PostRepository {
   private val postService=PostService

    suspend fun getPosts():Response<List<posts>>{
        return postService.postsApi.getPosts()
    }
}