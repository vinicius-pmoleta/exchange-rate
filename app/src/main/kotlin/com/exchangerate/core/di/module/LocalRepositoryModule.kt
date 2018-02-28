package com.exchangerate.core.di.module

import android.arch.persistence.room.Room
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.repository.local.database.ExchangeRateDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalRepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(application: ExchangeRateApplication): ExchangeRateDatabase {
        return Room.databaseBuilder(
                application.applicationContext,
                ExchangeRateDatabase::class.java,
                "exchange-rate-database"
        ).build()
    }
}