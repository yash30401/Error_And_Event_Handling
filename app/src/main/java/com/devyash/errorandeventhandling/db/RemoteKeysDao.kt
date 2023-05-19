package com.devyash.errorandeventhandling.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM flightremotekeys WHERE id=:id")
    suspend fun getRemoteKeys(id:String):FlightRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<FlightRemoteKeys>)

    @Query("DELETE FROM FlightRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}