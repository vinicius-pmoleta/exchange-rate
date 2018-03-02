package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.history.data.model.FailedHistoryResultAction
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.data.model.PrepareToLoadHistoryAction
import com.exchangerate.features.history.data.model.SuccessfulHistoryResultAction

class HistoryReducer : MviReducer<HistoryState> {

    override fun reduce(action: MviAction, state: HistoryState): HistoryState {
        return when (action) {
            is PrepareToLoadHistoryAction -> state.copy(isLoading = true)
            is SuccessfulHistoryResultAction -> state.copy(
                    isLoading = false,
                    data = action.data
            )
            is FailedHistoryResultAction -> state.copy(
                    isLoading = false,
                    error = action.error
            )
            else -> state
        }
    }

    override fun initialState(): HistoryState = HistoryState()
}