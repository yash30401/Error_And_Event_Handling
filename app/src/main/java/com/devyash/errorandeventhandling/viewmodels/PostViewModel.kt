package com.devyash.errorandeventhandling.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.devyash.errorandeventhandling.repositories.PostRepository

class PostViewModel(application: Application):AndroidViewModel(application) {

    private val postRepository:PostRepository

    init {
         postRepository=PostRepository()
    }

}