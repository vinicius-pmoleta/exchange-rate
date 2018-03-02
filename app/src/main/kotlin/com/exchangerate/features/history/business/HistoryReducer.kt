package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.history.data.HistoryState

class HistoryReducer : MviReducer<HistoryState> {

    override fun reduce(action: MviAction, state: HistoryState): HistoryState {
        return when (action) {
            else -> state
        }
    }

    override fun initialState(): HistoryState = HistoryState()
}