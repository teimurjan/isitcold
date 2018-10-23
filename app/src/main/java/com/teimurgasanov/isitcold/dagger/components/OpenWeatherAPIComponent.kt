package com.teimurgasanov.isitcold.dagger.components

import com.teimurgasanov.isitcold.dagger.modules.OpenWeatherAPIModule
import com.teimurgasanov.isitcold.ui.base.BaseView
import com.teimurgasanov.isitcold.ui.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(OpenWeatherAPIModule::class)])
interface OpenWeatherAPIComponent {
    fun inject(presenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): OpenWeatherAPIComponent

        fun networkModule(module: OpenWeatherAPIModule): Builder

        @BindsInstance
        fun baseView(view: BaseView): Builder
    }
}