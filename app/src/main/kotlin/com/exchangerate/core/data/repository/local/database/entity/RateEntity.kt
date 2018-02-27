package com.exchangerate.core.data.repository.local.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "rate")
class RateEntity {

    @PrimaryKey
    var currency: String = ""

    @ColumnInfo
    var rate: Float = 0F

    @ColumnInfo
    var base: String = ""

    @ColumnInfo
    var timestamp: Long = 0L
}