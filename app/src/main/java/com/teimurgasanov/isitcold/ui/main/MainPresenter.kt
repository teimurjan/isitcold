package com.teimurgasanov.isitcold.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.teimurgasanov.isitcold.network.OpenWeatherAPI
import com.teimurgasanov.isitcold.network.responses.ForecastResponse
import com.teimurgasanov.isitcold.ui.base.BasePresenter
import com.teimurgasanov.isitcold.ui.forecastItem.ForecastItemViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


const val DEFAULT_CITY = "Bishkek"
const val ACCESS_COARSE_LOCATION_CODE = 101

class MainPresenter(view: MainView) : BasePresenter<MainView>(view) {
    @Inject
    lateinit var api: OpenWeatherAPI


    private fun getForecast(cityName: String, days: Int) {
        view.startLoading()
        api.getForecast(cityName, days).enqueue(object : Callback<ForecastResponse> {

            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                response.body()?.let {
                    createListForView(it)
                    view.finishLoading()
                } ?: view.showError(ErrorTypes.NO_RESULT_FOUND)
            }

            override fun onFailure(call: Call<ForecastResponse>?, t: Throwable) {
                view.showError(ErrorTypes.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }

    private fun createListForView(response: ForecastResponse) {
        val forecasts = mutableListOf<ForecastItemViewModel>()
        for (forecast in response.forecast) {
            val forecastItem = ForecastItemViewModel(
                    temperature = forecast.temperature,
                    date = forecast.date,
                    icon = forecast.description[0].icon,
                    description = forecast.description[0].description
            )
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }

    private fun requestLocationPermission() {
        val context = view.getContext()
        val coarseStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (coarseStatus != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    ACCESS_COARSE_LOCATION_CODE
            )
        } else {
            getLocationWithForecast()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocationWithForecast() {
        val context = view.getContext()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
                .addOnSuccessListener { l: Location? ->
                    var city = DEFAULT_CITY
                    if (l != null) {
                        val gcd = Geocoder(context, Locale.getDefault())
                        val addresses = gcd.getFromLocation(l.latitude, l.longitude, 1)
                        if (addresses.size > 0) {
                            city = addresses[0].locality
                        }
                    }
                    view.setCity(city)
                    getForecast(city, 10)
                }
    }

    fun onCreate() {
        requestLocationPermission()
        getLocationWithForecast()
    }

    fun onRefresh() {
        getLocationWithForecast()
    }

    fun onRequestPermissionsResult(code: Int, grantResults: IntArray) {
        when (code) {
            ACCESS_COARSE_LOCATION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocationWithForecast()
                } else {
                    view.showPermissionLostDialog()
                    view.showError(ErrorTypes.NO_RESULT_FOUND)
                }
            }
        }
    }
}