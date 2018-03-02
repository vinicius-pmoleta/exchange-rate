package com.exchangerate.features.history.presentation.model

import android.arch.paging.PagedList
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity

data class HistoryItemScreenModel(
        val timestamp: String,
        var fromCurrency: String,
        var toCurrency: String,
        var valueToConvert: String,
        var convertedValue: String,
        var rate: String
)

data class HistoryScreenModel(
        val isLoading: Boolean = false,
        val history: PagedList<HistoryEntity>?
)