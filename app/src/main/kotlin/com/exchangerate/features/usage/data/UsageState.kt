package com.exchangerate.features.usage.data

import com.exchangerate.core.structure.MviState
import com.exchangerate.features.usage.data.Usage

data class UsageState(
        val isLoading: Boolean = false,
        val data: Usage? = null,
        val error: Throwable? = null
) : MviState