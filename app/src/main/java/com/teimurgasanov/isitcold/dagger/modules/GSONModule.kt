package com.teimurgasanov.isitcold.dagger.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()
}