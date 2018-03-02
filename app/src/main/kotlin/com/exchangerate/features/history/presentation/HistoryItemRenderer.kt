package com.exchangerate.features.history.presentation

import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.features.history.presentation.model.HistoryItemScreenModel
import java.text.SimpleDateFormat
import java.util.*

class HistoryItemRenderer {

    fun render(entity: HistoryEntity?) : HistoryItemScreenModel? {
        return entity?.let {
            HistoryItemScreenModel(
                    formatTimestamp(entity.timestamp),
                    entity.fromCurrency,
                    entity.toCurrency,
                    formatValue(entity.valueToConvert),
                    formatValue(entity.valueToConvert * entity.rate),
                    formatValue(entity.rate)
            )
        }
    }

    private fun formatTimestamp(timestamp: Long) : String {
        val timeFormat = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
        return timeFormat.format(Date(timestamp))
    }

    private fun formatValue(value: Float) : String {
        return String.format("%.3f", value)
    }
}