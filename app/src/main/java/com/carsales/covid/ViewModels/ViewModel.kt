package com.carsales.covid.ViewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carsales.covid.Interfaces.CovidDateListener
import com.carsales.covid.Models.CovidDateModel
import com.carsales.covid.Utility.HttpApiManager

class ViewModel: ViewModel() {

    val covidDateLiveData: MutableLiveData<CovidDateModel> = MutableLiveData()
    val appLoadingLiveData: MutableLiveData<Int> = MutableLiveData()
    val HttpApiManager = HttpApiManager()

    fun getApiCovidByDate(date: String){

        HttpApiManager.getApiCovidData(date, object : CovidDateListener {

            override fun onSuccess(covidDateModel: CovidDateModel?) {
                covidDateLiveData.postValue(covidDateModel)
                appLoadingLiveData.postValue(View.GONE)
            }

            override fun onError(error: Throwable) {
                covidDateLiveData.postValue(null)
                appLoadingLiveData.postValue(View.GONE)
            }

        })

    }
}