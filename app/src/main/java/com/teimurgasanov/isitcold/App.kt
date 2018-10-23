package com.teimurgasanov.isitcold


import android.app.Application
import com.teimurgasanov.isitcold.dagger.components.AppComponent
import com.teimurgasanov.isitcold.dagger.components.DaggerAppComponent
import com.teimurgasanov.isitcold.dagger.modules.AppModule

class App : Application() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    fun component() = component
}