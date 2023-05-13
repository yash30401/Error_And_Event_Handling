package com.devyash.errorandeventhandling.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object FlightService {
    private val flightApi:FlightApi

    init {
        val retrofit =  Retrofit.Builder()
            .baseUrl("https://api.instantwebtools.net/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flightApi = retrofit.create(FlightApi::class.java)
    }
}