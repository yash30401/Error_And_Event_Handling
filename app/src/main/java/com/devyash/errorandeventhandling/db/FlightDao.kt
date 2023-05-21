package com.devyash.errorandeventhandling.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devyash.errorandeventhandling.models.passenger.Data

@Dao
interface FlightDao {

    @Query("SELECT * FROM flight_table")
    fun getFlights():PagingSource<Int,Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFlight(flight:List<Data>)

    @Query("DELETE from flight_table")
    fun deleteFlight()
}