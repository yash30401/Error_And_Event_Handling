package com.devyash.errorandeventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.devyash.errorandeventhandling.adapter.FlightAdapter
import com.devyash.errorandeventhandling.viewmodels.FlightViewModel

class FlightDetails : AppCompatActivity() {

    private lateinit var flightAdapter: FlightAdapter
    private val flightViewModel:FlightViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_details)

       setupRecylerView()
    }

    private fun setupRecylerView() {

    }
}