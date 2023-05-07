package com.devyash.errorandeventhandling.api

import com.devyash.errorandeventhandling.models.posts
import com.devyash.errorandeventhandling.other.NetworkResult
import retrofit2.http.GET

interface PostsApi {

    @GET("/posts")
    suspend fun getPosts():NetworkResult<posts>
}