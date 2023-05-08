package com.devyash.errorandeventhandling.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devyash.errorandeventhandling.models.posts
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.repositories.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postRepository: PostRepository= PostRepository()
    private val _networkResult: MutableLiveData<NetworkResult<List<posts>>> = MutableLiveData()
    val networkResult: LiveData<NetworkResult<List<posts>>>get() = _networkResult


    fun getPosts()=viewModelScope.launch{
        getPostsSafeCall()
    }
    private suspend fun getPostsSafeCall(){
        _networkResult.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try{
                val response = postRepository.getPosts()
                if(response.code()==200){
                    val posts = response.body()
                    if(posts!=null && posts.isNotEmpty()) {
                        _networkResult.value = NetworkResult.Success(posts)
                    }else{
                        _networkResult.value = NetworkResult.Error("Empty or null response")
                    }

                } else {
                    _networkResult.value = NetworkResult.Error(response.message())
                }
            }catch (e:Exception){
                _networkResult.value = NetworkResult.Error(e.message)
            }
        }else{
            _networkResult.value = NetworkResult.Error("No Internet")
        }
    }


    private fun hasInternetConnection(): Boolean {
        return true
    }
}