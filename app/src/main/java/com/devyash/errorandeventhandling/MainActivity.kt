package com.devyash.errorandeventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.devyash.errorandeventhandling.databinding.ActivityMainBinding
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.viewmodels.PostViewModel

class MainActivity : AppCompatActivity() {

    private var _binding:ActivityMainBinding?=null
    val binding get() = _binding!!
    private val viewModel:PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        requestApiData()

    }

    private fun requestApiData() {
        viewModel.getPosts()
        viewModel.networkResult.observe(this, Observer {response->
            when(response){
                is NetworkResult.Success->{
                    response?.let {
                    binding.tvResult.text = it.toString()
                    }
                }
                is NetworkResult.Error->{
                   Log.d("RESPONSECALL",response.toString())
                }
                is NetworkResult.Loading->{
                    Log.d("RESPONSECALL",response.toString())
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}