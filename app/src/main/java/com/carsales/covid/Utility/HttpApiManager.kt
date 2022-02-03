package com.carsales.covid.Utility

import com.carsales.covid.Interfaces.ApiCovidDateService
import com.carsales.covid.Interfaces.CovidDateListener
import com.carsales.covid.Models.CovidDateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HttpApiManager {

    private val HEADER = "96afa298cbmsh913f910f914494cp110c39jsn01a32d68445e"
    private val base_url = "https://covid-19-statistics.p.rapidapi.com/"

    private val call: Retrofit = Retrofit.Builder().baseUrl(base_url)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(
            GsonConverterFactory.create())
        .build()

    fun getApiCovidData(date: String,  listener: CovidDateListener) {
        call.create(ApiCovidDateService::class.java).getApi(HEADER, date).enqueue(object :
            Callback<CovidDateModel?> {
            override fun onResponse(call: Call<CovidDateModel?>, response: Response<CovidDateModel?>) {
                listener.onSuccess(response.body())
            }
            override fun onFailure(call: Call<CovidDateModel?>, t: Throwable) {
                listener.onError(t)
            }
        });
    }
}