package com.devyash.errorandeventhandling.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devyash.errorandeventhandling.models.passenger.Data

@Database(entities = [Data::class,FlightRemoteKeys::class], version = 1)
abstract class FlightDatabase:RoomDatabase() {
        abstract fun flightDao():FlightDao
        abstract fun remoteKeysDao():RemoteKeysDao

        companion object{
            @Volatile
            private var INSTANCE:FlightDatabase?=null

            fun getDatabase(context: Context):FlightDatabase{
                return INSTANCE?: synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlightDatabase::class.java,
                        "flight_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }
}