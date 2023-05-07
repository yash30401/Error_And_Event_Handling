package com.devyash.errorandeventhandling.repositories

import com.devyash.errorandeventhandling.api.PostService
import com.devyash.errorandeventhandling.models.posts
import com.devyash.errorandeventhandling.other.NetworkResult

class PostRepository {
   private val postService=PostService

    suspend fun getPosts():NetworkResult<posts>{
        return postService.postsApi.getPosts()
    }
}