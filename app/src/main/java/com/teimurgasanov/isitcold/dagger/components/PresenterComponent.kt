package com.teimurgasanov.isitcold.dagger.components

import com.teimurgasanov.isitcold.dagger.modules.OpenWeatherAPIModule
import com.teimurgasanov.isitcold.dagger.modules.SharedPreferencesModule
import com.teimurgasanov.isitcold.ui.base.BaseView
import com.teimurgasanov.isitcold.ui.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(OpenWeatherAPIModule::class), (SharedPreferencesModule::class)])
interface PresenterComponent {
    fun inject(presenter: MainPresenter)


    @Component.Builder
    interface Builder {
        fun build(): PresenterComponent

        fun apiModule(module: OpenWeatherAPIModule): Builder

        fun prefsModule(module: SharedPreferencesModule): Builder

        @BindsInstance
        fun baseView(view: BaseView): Builder
    }
}