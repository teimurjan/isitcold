package com.teimurgasanov.isitcold.dagger.modules

import com.google.gson.Gson
import com.teimurgasanov.isitcold.network.OpenWeatherAPI
import com.teimurgasanov.isitcold.network.interceptors.OpenWeatherInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [(GSONModule::class)])
object OpenWeatherAPIModule {
    @Provides
    @Singleton
    fun provide(gson: Gson): OpenWeatherAPI {
        val apiClient = OkHttpClient.Builder().addInterceptor(OpenWeatherInterceptor()).build()
        return Retrofit
                .Builder()
                .apply {
                    baseUrl(OpenWeatherAPI.BASE_URL)
                    addConverterFactory(GsonConverterFactory.create(gson))
                    client(apiClient)
                }
                .build()
                .create(OpenWeatherAPI::class.java)
    }
}