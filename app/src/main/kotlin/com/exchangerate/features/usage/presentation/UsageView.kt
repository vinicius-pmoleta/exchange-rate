package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageScreenModel

interface UsageView: MviView<UsageIntent, UsageState> {

    fun renderLoading(isLoading: Boolean)

    fun renderData(usage: UsageScreenModel)

    fun renderError()
}