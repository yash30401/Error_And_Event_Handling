package com.devyash.errorandeventhandling.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.devyash.errorandeventhandling.api.FlightApi
import com.devyash.errorandeventhandling.api.FlightService
import com.devyash.errorandeventhandling.paging.FlightPagingSource
import retrofit2.Response

class FlightRepository {
    private val flightService = FlightService

    fun getFlightDetails() = Pager(
        config = PagingConfig(20, maxSize = 100),
        pagingSourceFactory = {FlightPagingSource(flightService.flightApi)}
    ).flow

}