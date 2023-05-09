# Error_And_Event_Handling
#### So, I've been working on this repo to get better at dealing with network errors. I wanted to learn the best ways to handle them and put that knowledge into practice. By doing this, I hope toget really good at fixing any hiccups that come up while staying connected online. It's been a fun learning experience!

``` kotlin
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}
```
#### posts is our class that holds list of postItems and postItems is our data class.
#### Our networkResult will hold the response which we get from NetwrokResult.
``` kotlin
   private val _networkResult: MutableLiveData<NetworkResult<posts>> = MutableLiveData()
   val networkResult: LiveData<NetworkResult<posts>>get() = _networkResult
```

#### Then we call getPosts().
``` kotlin
    fun getPosts()=viewModelScope.launch{
        getPostsSafeCall()
    }
```
``` kotlin
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
```

#### And this is how we will handle it in the main activity.
``` kotlin
private fun requestApiData() {
        lifecycleScope.launch {
            viewModel.getPosts()
            lifecycleScope.launch {
                viewModel.networkResult.observe(this@MainActivity, Observer { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            response.data?.let {posts->
                                var res=""
                                posts.forEach {
                                       res += it.title+"\n\n"

                                   }
                                GlobalScope.launch(Dispatchers.Main) {
                                    binding.tvResult.text = res
                                }
                            }

                        }
                        is NetworkResult.Error -> {
                            Log.d("RESPONSECALL", response.message.toString())
                        }
                        is NetworkResult.Loading -> {
                            Log.d("RESPONSECALL", response.message.toString())
                        }
                    }
                })
            }
        }
    }
```
