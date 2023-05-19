package com.devyash.errorandeventhandling.db

import androidx.room.TypeConverter
import com.devyash.errorandeventhandling.models.passenger.Airline
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AirlineConverter {

    @TypeConverter
    fun fromListToString(value:List<Airline>):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToList(value:String):List<Airline>{
        val type = object :TypeToken<List<Airline>>(){}.type
        return Gson().fromJson(value,type)
    }

}