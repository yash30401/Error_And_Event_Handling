package com.devyash.errorandeventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyash.errorandeventhandling.adapter.PostsAdapter
import com.devyash.errorandeventhandling.databinding.ActivityMainBinding
import com.devyash.errorandeventhandling.models.postsItem
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.viewmodels.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        requestApiData()


    }


    private fun requestApiData() {
        lifecycleScope.launch {
            viewModel.getPosts()
            lifecycleScope.launch {
                viewModel.networkResult.observe(this@MainActivity, Observer { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            response.data?.let {posts->
                                val posItemList:List<postsItem> = posts.toList()
                                GlobalScope.launch(Dispatchers.Main) {
                                    binding.recylerview.apply {
                                        postsAdapter = PostsAdapter(posItemList)
                                        adapter = postsAdapter
                                        layoutManager = LinearLayoutManager(this@MainActivity)
                                    }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}