package com.exchangerate.core.data.repository.local.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.data.repository.local.database.entity.RateEntity
import com.exchangerate.features.conversion.data.CurrenciesDao
import com.exchangerate.features.conversion.data.RateDao
import com.exchangerate.features.history.data.HistoryDao

@Database(
        entities = [CurrencyEntity::class, RateEntity::class, HistoryEntity::class],
        version = 1
)
abstract class ExchangeRateDatabase : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao

    abstract fun rateDao(): RateDao

    abstract fun historyDao(): HistoryDao
}