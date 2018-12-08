package com.teimurgasanov.isitcold.network

import com.teimurgasanov.isitcold.network.responses.ForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    @GET("forecast/daily/")
    fun getForecast(@Query("q") city: String, @Query("cnt") days: Int): Call<ForecastResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}