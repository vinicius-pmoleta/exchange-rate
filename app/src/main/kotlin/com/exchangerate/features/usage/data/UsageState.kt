package com.exchangerate.features.usage.data

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviState

data class UsageState(
        val isLoading: Boolean = false,
        val data: Usage? = null,
        val error: Throwable? = null
) : MviState