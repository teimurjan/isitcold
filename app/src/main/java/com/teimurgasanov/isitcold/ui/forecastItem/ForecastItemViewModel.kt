package com.teimurgasanov.isitcold.ui.forecastItem

import com.teimurgasanov.isitcold.models.Temperature

data class ForecastItemViewModel(
        val temperature: Temperature,
        val icon: String = "01d",
        val date: Long = System.currentTimeMillis(),
        val description: String = ""
)