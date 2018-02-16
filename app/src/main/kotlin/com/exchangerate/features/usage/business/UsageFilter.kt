package com.exchangerate.features.usage.business

import com.exchangerate.core.structure.MviFilter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.data.SuccessfulUsageResultAction
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageInitialIntent
import com.exchangerate.features.usage.presentation.model.UsageIntent

class UsageFilter(
        private val store: MviStore<UsageState>
) : MviFilter<UsageIntent, UsageState> {

    override fun apply(intent: UsageIntent, state: UsageState?): Boolean {
        var shouldFilter = true
        when (intent) {
            is UsageInitialIntent -> state?.data?.let {
                store.dispatch(SuccessfulUsageResultAction(it))
                shouldFilter = false
            }
        }
        return shouldFilter
    }
}