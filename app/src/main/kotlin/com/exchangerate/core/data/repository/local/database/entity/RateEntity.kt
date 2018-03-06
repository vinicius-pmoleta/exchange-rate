package com.exchangerate.core.data.repository.local.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "rate")
data class RateEntity(
        @PrimaryKey var currency: String,
        @ColumnInfo var rate: Float,
        @ColumnInfo var base: String,
        @ColumnInfo var timestamp: Long
)