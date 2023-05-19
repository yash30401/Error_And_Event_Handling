package com.devyash.errorandeventhandling.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlightRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val prevKey:Int?,
    val nextKey:Int?
)
