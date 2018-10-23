package com.teimurgasanov.isitcold.models

import com.google.gson.annotations.SerializedName

data class Temperature(
        @SerializedName("day") var atDay: Double,
        @SerializedName("night") var atNight: Double
)