package com.devyash.errorandeventhandling.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devyash.errorandeventhandling.api.FlightApi
import com.devyash.errorandeventhandling.api.PostsApi
import com.devyash.errorandeventhandling.models.passenger.Data
import com.devyash.errorandeventhandling.models.passenger.passengers

class FlightPagingSource(val flightApi: FlightApi) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val position = params.key ?: 1
            val response = flightApi.getPassengersData(position)
            LoadResult.Page(
                data = response.body()?.data!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.body()?.totalPages) null else position + 1

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}