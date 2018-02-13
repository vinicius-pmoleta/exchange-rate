package com.exchangerate.features.usage.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.usage.data.LoadUsageAction
import com.exchangerate.features.usage.data.StartLoadingUsageAction
import com.exchangerate.features.usage.presentation.model.LoadUsageIntent
import com.exchangerate.features.usage.presentation.model.UsageInitialIntent
import com.exchangerate.features.usage.presentation.model.UsageIntent

class UsageInterpreter : MviIntentInterpreter<UsageIntent> {

    override fun translate(intent: UsageIntent): List<MviAction> {
        return when (intent) {
            is UsageInitialIntent -> listOf(StartLoadingUsageAction(), LoadUsageAction())
            is LoadUsageIntent -> listOf(StartLoadingUsageAction(), LoadUsageAction())
            else -> emptyList()
        }
    }
}