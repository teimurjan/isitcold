package com.teimurgasanov.isitcold.network.responses

import com.google.gson.annotations.SerializedName
import com.teimurgasanov.isitcold.models.City
import com.teimurgasanov.isitcold.models.Forecast

data class ForecastResponse (
        @SerializedName("city") var city: City,
        @SerializedName("list") var forecast: List<Forecast>
)