package com.devyash.errorandeventhandling.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devyash.errorandeventhandling.models.passenger.Data
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.repositories.FlightRepository
import kotlinx.coroutines.launch

class FlightViewModel(application: Application) : AndroidViewModel(application) {

    private val flightRepository = FlightRepository()
    private val _networkResult: MutableLiveData<NetworkResult<Data>> = MutableLiveData()
    val networkResult: LiveData<NetworkResult<Data>>
        get() = _networkResult

    fun getFlightData() = viewModelScope.launch {
        getFlightDataSafeCall()
    }

    private suspend fun getFlightDataSafeCall() {
        _networkResult.value = NetworkResult.Loading()

        if(hasInternetConnection()){}
    }

    private fun hasInternetConnection(): Boolean {
        return true
    }
}