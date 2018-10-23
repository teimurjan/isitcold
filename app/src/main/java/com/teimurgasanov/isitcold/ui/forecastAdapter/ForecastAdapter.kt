package com.teimurgasanov.isitcold.ui.forecastAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.teimurgasanov.isitcold.R
import com.teimurgasanov.isitcold.models.Forecast
import com.teimurgasanov.isitcold.ui.forecastItem.ForecastItemViewModel
import kotlinx.android.synthetic.main.forecast_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter : RecyclerView.Adapter<ViewHolder>() {

    var forecastList = mutableListOf<ForecastItemViewModel>()

    fun addForecast(list: List<ForecastItemViewModel>) {
        forecastList.clear()
        forecastList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.degreeTextDay?.text = getDegreeText(forecast)
        holder.degreeTextNight?.text = getDegreeText(forecast, atDay = false)

        val date = getDate(forecast.date)
        holder.dayText.text = date

        Glide.with(holder.context)
                .load("http://openweathermap.org/img/w/${forecast.icon}.png")
                .into(holder.weatherIconDay)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    private fun getDate(date: Long): String {
        val timeFormatter = SimpleDateFormat("EEEE")
        return timeFormatter.format(Date(date * 1000L))
    }

    private fun getDegreeText(forecast: ForecastItemViewModel, atDay: Boolean = true): String {
        if (atDay)
            return "${forecast.temperature.atDay.toInt()}°";
        return "${forecast.temperature.atNight.toInt()}°";
    }

}

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val context = v.context!!
    val degreeTextDay = v.degreeTextDay
    val degreeTextNight = v.degreeTextNight
    val dayText = v.dayText
    val weatherIconDay = v.weatherIconDay
}