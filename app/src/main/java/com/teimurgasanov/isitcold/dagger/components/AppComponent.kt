package com.teimurgasanov.isitcold.dagger.components

import com.teimurgasanov.isitcold.dagger.modules.AppModule
import com.teimurgasanov.isitcold.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}