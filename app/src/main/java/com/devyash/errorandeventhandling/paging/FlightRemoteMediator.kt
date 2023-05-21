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
class FlightRemoteMediator(private val flightApi: FlightApi, private val flightDatabase: FlightDatabase) : RemoteMediator<Int, Data>() {

    private val flightDao = flightDatabase.flightDao()
    private val flightRemoteKeysDao = flightDatabase.remoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Data>): MediatorResult {
        //Fetch flights Data from Api
        //Save these flights + remotekyes Data into DB
        //Logic for states - REFRESH, PREPEND, APPEND

        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = flightApi.getPassengersData(currentPage)
            val endOfPaginationReached = response.body()?.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            flightDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    flightDao.deleteFlight()
                    flightRemoteKeysDao.deleteAllRemoteKeys()
                }

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

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Data>
    ): FlightRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                flightRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Data>): FlightRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { flight ->
                flightRemoteKeysDao.getRemoteKeys(id = flight._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Data>): FlightRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { flight ->
                flightRemoteKeysDao.getRemoteKeys(id = flight._id)
            }
    }

}