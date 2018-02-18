package com.exchangerate.features.usage.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.usage.data.FailedUsageResultAction
import com.exchangerate.features.usage.data.PrepareToFetchUsageAction
import com.exchangerate.features.usage.data.SuccessfulUsageResultAction
import com.exchangerate.features.usage.data.UsageState

class UsageReducer : MviReducer<UsageState> {

    override fun reduce(action: MviAction, state: UsageState): UsageState {
        return when (action) {
            is PrepareToFetchUsageAction -> state.copy(isLoading = true)
            is SuccessfulUsageResultAction -> state.copy(
                    isLoading = false,
                    data = action.usage
            )
            is FailedUsageResultAction -> state.copy(
                    isLoading = false,
                    error = action.error
            )
            else -> state
        }
    }

    override fun initialState(): UsageState = UsageState()
}