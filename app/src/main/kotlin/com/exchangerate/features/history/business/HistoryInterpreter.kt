package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.history.data.HistoryAction
import com.exchangerate.features.history.presentation.model.HistoryIntent

class HistoryInterpreter : MviIntentInterpreter<HistoryIntent, HistoryAction> {

    override fun translate(intent: HistoryIntent): List<HistoryAction> {
        return when (intent) {
            else -> emptyList()
        }
    }
}