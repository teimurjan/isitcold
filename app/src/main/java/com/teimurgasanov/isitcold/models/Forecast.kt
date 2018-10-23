package com.teimurgasanov.isitcold.models

import com.google.gson.annotations.SerializedName


data class Forecast(
        @SerializedName("dt") var date: Long,
        @SerializedName("temp") var temperature: Temperature,
        @SerializedName("weather") var description: List<Weather>,
        @SerializedName("pressure") var pressure: Double,
        @SerializedName("humidity") var humidity: Double
)