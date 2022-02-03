package com.carsales.covid.Interfaces

import com.carsales.covid.Models.CovidDateModel

interface CovidDateListener {
    fun onSuccess(covidModel: CovidDateModel?)
    fun onError(error: Throwable)
}