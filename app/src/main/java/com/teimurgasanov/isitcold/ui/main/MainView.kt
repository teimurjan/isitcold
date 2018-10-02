package com.teimurgasanov.isitcold.ui.main

import com.teimurgasanov.isitcold.ui.base.BaseView
import com.teimurgasanov.isitcold.ui.forecastItem.ForecastItemViewModel

interface MainView : BaseView {
    fun startLoading()
    fun finishLoading()
    fun updateForecast(forecasts: List<ForecastItemViewModel>)
    fun showErrorToast(errorType: ErrorTypes)
    fun setCity(city: String)
}