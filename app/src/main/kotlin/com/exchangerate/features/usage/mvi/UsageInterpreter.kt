package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviIntentInterpreter

class UsageInterpreter : MviIntentInterpreter<UsageIntent> {

    override fun translate(intent: UsageIntent): List<MviAction> {
        return when (intent) {
            is LoadUsageIntent -> listOf(StartLoadingUsageAction(), LoadUsageAction())
            else -> emptyList()
        }
    }
}