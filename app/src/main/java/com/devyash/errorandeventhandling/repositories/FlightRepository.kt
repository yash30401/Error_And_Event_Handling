package com.devyash.errorandeventhandling.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.devyash.errorandeventhandling.api.FlightApi
import com.devyash.errorandeventhandling.api.FlightService
import com.devyash.errorandeventhandling.db.FlightDatabase
import com.devyash.errorandeventhandling.paging.FlightPagingSource
import com.devyash.errorandeventhandling.paging.FlightRemoteMediator
import retrofit2.Response

class FlightRepository(private val flightDatabase: FlightDatabase) {
    private val flightService = FlightService

    @OptIn(ExperimentalPagingApi::class)
    fun getFlightDetails() = Pager(
        config = PagingConfig(20, maxSize = 100),
        remoteMediator = FlightRemoteMediator(flightService.flightApi,flightDatabase),
        pagingSourceFactory = {FlightPagingSource(flightService.flightApi)}
    ).flow

}