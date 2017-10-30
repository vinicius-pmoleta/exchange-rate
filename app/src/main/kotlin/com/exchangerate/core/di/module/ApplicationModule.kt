package com.exchangerate.core.di.module

import android.content.Context
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.di.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: ExchangeRateApplication) {

    @Provides
    @Singleton
    fun providesApplication(): ExchangeRateApplication {
        return application
    }

    @Provides
    @Singleton
    @ForApplication
    fun providesContext(): Context {
        return application.applicationContext
    }

}