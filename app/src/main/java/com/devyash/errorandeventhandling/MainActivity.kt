package com.devyash.errorandeventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.devyash.errorandeventhandling.databinding.ActivityMainBinding
import com.devyash.errorandeventhandling.models.postsItem
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.viewmodels.PostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
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
                            response.data?.let {

                                GlobalScope.launch(Dispatchers.Main) {
                                    binding.tvResult.text =it[0].postItems[0].title.toString()
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