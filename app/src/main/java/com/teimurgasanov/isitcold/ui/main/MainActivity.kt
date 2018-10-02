package com.teimurgasanov.isitcold.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.teimurgasanov.isitcold.R
import com.teimurgasanov.isitcold.ui.base.BaseActivity
import com.teimurgasanov.isitcold.ui.forecastAdapter.ForecastAdapter
import com.teimurgasanov.isitcold.ui.forecastItem.ForecastItemViewModel


class MainActivity : BaseActivity<MainPresenter>(), MainView {
    private lateinit var spinner: ProgressBar
    private lateinit var emptyStateText: TextView
    private lateinit var forecastList: RecyclerView
    private lateinit var forecast: LinearLayout
    private lateinit var location: TextView
    private lateinit var todayDegree: TextView
    private lateinit var todayDescription: TextView


    private var errorMsgOf = mapOf(
            ErrorTypes.CALL_ERROR to R.string.error_common
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.spinner = findViewById(R.id.loadingSpinner)
        this.emptyStateText = findViewById(R.id.emptyStateText)
        this.forecastList = findViewById(R.id.forecastList)
        this.forecast = findViewById(R.id.forecast)
        this.location = findViewById(R.id.location)
        this.todayDegree = findViewById(R.id.todayDegree)
        this.todayDescription = findViewById(R.id.todayDescription)

        initializeForecastList()
        presenter.onCreate()
    }


    private fun initializeForecastList() {
        forecastList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastAdapter()
        }
    }

    override fun startLoading() {
        forecast.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        spinner.visibility = View.VISIBLE;
    }

    override fun finishLoading() {
        forecast.visibility = View.VISIBLE
        spinner.visibility = View.GONE;
    }

    override fun updateForecast(forecasts: List<ForecastItemViewModel>) {
        if (forecasts.isEmpty()) emptyStateText.visibility = View.VISIBLE
        (forecastList.adapter as ForecastAdapter).addForecast(
                forecasts.slice(1 until forecasts.size)
        )
        todayDegree.text = "${forecasts[0].temperature.atDay.toInt()}°"
        todayDescription.text = forecasts[0].description
    }

    override fun showErrorToast(errorType: ErrorTypes) {
        errorMsgOf[errorType]?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show();
        }
        emptyStateText.visibility = View.VISIBLE;
    }

    override fun requestPermission(permission: String, code: Int) {
        val permissionRequestDeclined = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        if (permissionRequestDeclined) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.permission_missed_title)
                    .setMessage(R.string.permission_missed_description)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }
            val alert = builder.create()
            alert.show()
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    code
            )
        }
    }

    override fun onRequestPermissionsResult(code: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(code, permissions, grantResults)
        presenter.onRequestPermissionsResult(code, grantResults)

    }

    override fun setCity(city: String) {
        location.text = city
    }

    override fun instantiatePresenter(): MainPresenter {
        return MainPresenter(this)
    }
}