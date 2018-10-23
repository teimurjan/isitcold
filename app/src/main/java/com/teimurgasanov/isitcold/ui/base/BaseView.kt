package com.teimurgasanov.isitcold.ui.base


import android.content.Context

/**
 * Base view any view must implement.
 */
interface BaseView {
    /**
     * Returns the Context in which the application is running.
     * @return the Context in which the application is running
     */
    fun getContext(): Context
    fun requestPermission(permission: String, code: Int)
}