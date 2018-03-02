package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviFilter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.data.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryIntent

class HistoryFilter(
        private val store: MviStore<HistoryState>
) : MviFilter<HistoryIntent, HistoryState> {

    override fun apply(intent: HistoryIntent, state: HistoryState?): Boolean {
        var shouldFilter = true
        when (intent) {
        }
        return shouldFilter
    }
}