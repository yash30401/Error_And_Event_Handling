package com.devyash.errorandeventhandling.api

import com.devyash.errorandeventhandling.models.passenger.passengers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApi {

    @GET("passenger")
    suspend fun getPassengersData(
        @Query("page") page:Int,
        @Query("size") size:Int?=20
    ):Response<passengers>

}