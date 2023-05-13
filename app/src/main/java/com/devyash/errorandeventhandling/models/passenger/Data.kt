package com.devyash.errorandeventhandling.models.passenger

data class Data(
    val __v: Int,
    val _id: String,
    val airline: List<Airline>,
    val name: String,
    val trips: Int
)