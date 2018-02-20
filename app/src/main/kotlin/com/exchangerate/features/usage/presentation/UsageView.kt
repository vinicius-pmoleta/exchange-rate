package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageIntent
import com.exchangerate.features.usage.presentation.model.UsageScreenModel

interface UsageView : MviView<UsageIntent, UsageState> {

    fun renderData(usage: UsageScreenModel)

    fun renderError()
}