package com.exchangerate.core.data.repository.local.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
        @PrimaryKey var timestamp: Long,
        @ColumnInfo var fromCurrency: String,
        @ColumnInfo var toCurrency: String,
        @ColumnInfo var valueToConvert: Float,
        @ColumnInfo var rate: Float
)