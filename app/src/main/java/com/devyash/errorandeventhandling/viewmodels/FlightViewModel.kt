package com.devyash.errorandeventhandling.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devyash.errorandeventhandling.models.passenger.Data
import com.devyash.errorandeventhandling.other.NetworkResult
import com.devyash.errorandeventhandling.repositories.FlightRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.lang.Exception

class FlightViewModel(application: Application) : AndroidViewModel(application) {

    private val flightRepository = FlightRepository()
    private val _networkResult: MutableLiveData<NetworkResult<LiveData<PagingData<Data>>>> = MutableLiveData()
    val networkResult: LiveData<NetworkResult<LiveData<PagingData<Data>>>>
        get() = _networkResult

    fun getFlightData() = viewModelScope.launch {
        getFlightDataSafeCall()
    }

    private suspend fun getFlightDataSafeCall() {
        _networkResult.value = NetworkResult.Loading()

        if(hasInternetConnection()){

            try {
               _networkResult.value = NetworkResult.Success(flightRepository.getFlightDetails().asLiveData().cachedIn(viewModelScope))

            }catch (e:Exception){
                _networkResult.value = NetworkResult.Error(e.message)
            }

        }else{
            _networkResult.value = NetworkResult.Error("No Internet")
        }
    }

    private fun hasInternetConnection(): Boolean {
        return true
    }
}