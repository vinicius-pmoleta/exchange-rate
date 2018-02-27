package com.exchangerate.core.data.repository.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.local.database.entity.RateEntity
import com.exchangerate.features.conversion.data.ConversionDao

@Database(
        entities = [CurrencyEntity::class, RateEntity::class],
        version = 1
)
abstract class ExchangeRateDatabase : RoomDatabase() {

    abstract fun conversionDao(): ConversionDao
}