package com.devyash.errorandeventhandling.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.devyash.errorandeventhandling.api.FlightApi
import com.devyash.errorandeventhandling.api.FlightService
import com.devyash.errorandeventhandling.db.FlightDatabase
import com.devyash.errorandeventhandling.db.FlightRemoteKeys
import com.devyash.errorandeventhandling.models.passenger.Data

@ExperimentalPagingApi
class FlightRemoteMediator(context: Context) : RemoteMediator<Int, Data>() {
    private val flightaApi = FlightService.flightApi
    private val flightDatabase = FlightDatabase.getDatabase(context)

    private val flightDao = flightDatabase.flightDao()
    private val flightRemoteKeysDao = flightDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
        //Fetch flights Data from Api
        //Save these flights + remotekyes Data into DB
        //Logic for states - REFRESH, PREPEND, APPEND

        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {}
                LoadType.PREPEND -> {}
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = flightaApi.getPassengersData(currentPage)
            val endOfPaginationReached = response.body()?.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            flightDatabase.withTransaction {
                flightDao.addFlight(response.body()?.data!!)
                val keys = response.body()?.data?.map { flight ->
                    FlightRemoteKeys(
                        id = flight._id,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }
                flightRemoteKeysDao.addAllRemoteKeys(keys!!)
            }
            MediatorResult.Success(endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Data>): FlightRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { flight ->
                flightRemoteKeysDao.getRemoteKeys(id = flight._id)
            }
    }

}