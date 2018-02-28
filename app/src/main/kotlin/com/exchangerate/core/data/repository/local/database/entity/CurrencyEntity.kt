package com.exchangerate.core.data.repository.local.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
        @PrimaryKey var code: String = "",
        @ColumnInfo var name: String = ""
)