package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviViewState
import com.exchangerate.features.usage.data.Usage

data class UsageViewState(
        val isLoading: Boolean = false,
        val usage: Usage? = null,
        val error: Throwable? = null) : MviViewState