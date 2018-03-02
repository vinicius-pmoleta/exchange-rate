package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.history.data.model.HistoryAction
import com.exchangerate.features.history.data.model.LoadHistoryAction
import com.exchangerate.features.history.data.model.PrepareToLoadHistoryAction
import com.exchangerate.features.history.presentation.model.HistoryInitialIntent
import com.exchangerate.features.history.presentation.model.HistoryIntent

class HistoryInterpreter : MviIntentInterpreter<HistoryIntent, HistoryAction> {

    override fun translate(intent: HistoryIntent): List<HistoryAction> {
        return when (intent) {
            is HistoryInitialIntent -> listOf(PrepareToLoadHistoryAction(), LoadHistoryAction())
            else -> emptyList()
        }
    }
}