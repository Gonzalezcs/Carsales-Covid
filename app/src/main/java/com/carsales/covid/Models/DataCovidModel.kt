package com.carsales.covid.Models

data class DataCovidModel (
    val date: String,
    val last_update: String,
    val confirmed: Int,
    val deaths: Int,
    val confirmed_diff : Int,
    val deaths_diff : Int,
    val recovered : Int,
    val recovered_diff : Int,
    val active : Int,
    val active_diff : Int,
    val fatality_rate : Float
)
