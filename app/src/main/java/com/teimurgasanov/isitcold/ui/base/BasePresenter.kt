package com.teimurgasanov.isitcold.ui.base

import com.teimurgasanov.isitcold.dagger.components.DaggerPresenterComponent
import com.teimurgasanov.isitcold.dagger.components.PresenterComponent
import com.teimurgasanov.isitcold.dagger.modules.OpenWeatherAPIModule
import com.teimurgasanov.isitcold.dagger.modules.SharedPreferencesModule
import com.teimurgasanov.isitcold.ui.main.MainPresenter

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @property component The component used to inject required dependencies
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {
    private val component: PresenterComponent = DaggerPresenterComponent
            .builder()
            .baseView(view)
            .apiModule(OpenWeatherAPIModule)
            .prefsModule(SharedPreferencesModule(view.getContext()))
            .build()


    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainPresenter -> component.inject(this)

        }
    }
}