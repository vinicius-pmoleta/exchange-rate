package com.exchangerate.features.conversion.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.exchangerate.core.data.repository.local.database.entity.RateEntity

@Dao
interface ConversionDao {

    @Query("SELECT * FROM rate WHERE currency = :currency")
    fun getRateForCurrency(currency: String): RateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rates: List<RateEntity>)
}