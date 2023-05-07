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

    private val postRepository: PostRepository
    private val _networkResult: MutableLiveData<NetworkResult<posts>> = MutableLiveData()
    val networkResult: LiveData<NetworkResult<posts>>

    init {
        postRepository = PostRepository()
        networkResult = _networkResult
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun getPosts()=viewModelScope.launch(Dispatchers.IO){
        getPostsSafeCall()
    }
    private suspend fun getPostsSafeCall(){
        _networkResult.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try{
                val response = postRepository.getPosts()
                if(response.code()==200){
                    _networkResult.value = NetworkResult.Success(response.body()!!)
                } else {
                    _networkResult.value = NetworkResult.Error(null,response.message())
                }
            }catch (e:Exception){
                _networkResult.value = NetworkResult.Error(null,e.message)
            }
        }else{
            _networkResult.value = NetworkResult.Error(null,"No Internet")
        }
    }

    private fun hasInternetConnection(): Boolean {
        return true
    }
}