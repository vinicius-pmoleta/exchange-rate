package com.exchangerate.core.di.module

import com.exchangerate.core.ExchangeRateApplication
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

}