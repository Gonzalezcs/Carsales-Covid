package com.carsales.covid.Interfaces

import com.carsales.covid.Models.CovidDateModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiCovidDateService {

    @GET("reports/total")
    @Headers("Content-Type: application/json")
    fun getApi(@Header("X-RapidAPI-Key") apiKey: String, @Query("date") date: String): Call<CovidDateModel>

}