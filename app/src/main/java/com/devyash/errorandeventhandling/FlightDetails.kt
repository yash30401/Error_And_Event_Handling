package com.devyash.errorandeventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyash.errorandeventhandling.adapter.FlightAdapter
import com.devyash.errorandeventhandling.adapter.LoaderAdapter
import com.devyash.errorandeventhandling.databinding.ActivityFlightDetailsBinding
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.viewmodels.FlightViewModel
import kotlinx.coroutines.launch

class FlightDetails : AppCompatActivity() {

    private lateinit var flightAdapter: FlightAdapter
    private val flightViewModel: FlightViewModel by viewModels()

    private lateinit var binding: ActivityFlightDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fightRecylerView.apply {
            flightAdapter = FlightAdapter()
            adapter= flightAdapter.withLoadStateHeaderAndFooter(
                header = LoaderAdapter(),
                footer = LoaderAdapter()
            )
            layoutManager  = LinearLayoutManager(this@FlightDetails)
        }

        getFlightData()
    }

    private fun getFlightData() {
        lifecycleScope.launch {
            flightViewModel.getFlightData()
            flightViewModel.networkResult.observe(this@FlightDetails, Observer {response->
                when(response){
                    is NetworkResult.Success->{
                        response.data?.let {
                            it.observe(this@FlightDetails, Observer {data->

                                flightAdapter.submitData(lifecycle,data)
                            })
                        }
                    }
                    is NetworkResult.Error->{
                        Log.d("RESPONSECALL", response.message.toString())
                    }
                    is NetworkResult.Loading->{
                        Log.d("RESPONSECALL", response.message.toString())
                    }
                }
            })
        }
    }
}