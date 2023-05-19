package com.devyash.errorandeventhandling.models.passenger

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.devyash.errorandeventhandling.db.AirlineConverter

@Entity(tableName = "flight_table")
@TypeConverters(AirlineConverter::class)
data class Data(
    val __v: Int,
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val airline: List<Airline>,
    val name: String,
    val trips: Int
)