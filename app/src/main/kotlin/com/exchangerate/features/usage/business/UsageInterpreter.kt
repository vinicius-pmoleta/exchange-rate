package com.exchangerate.features.usage.business

import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.usage.data.FetchUsageAction
import com.exchangerate.features.usage.data.PrepareToFetchUsageAction
import com.exchangerate.features.usage.data.UsageAction
import com.exchangerate.features.usage.presentation.model.LoadUsageIntent
import com.exchangerate.features.usage.presentation.model.UsageInitialIntent
import com.exchangerate.features.usage.presentation.model.UsageIntent

class UsageInterpreter : MviIntentInterpreter<UsageIntent, UsageAction> {

    override fun translate(intent: UsageIntent): List<UsageAction> {
        return when (intent) {
            is UsageInitialIntent -> listOf(PrepareToFetchUsageAction(), FetchUsageAction())
            is LoadUsageIntent -> listOf(PrepareToFetchUsageAction(), FetchUsageAction())
            else -> emptyList()
        }
    }
}