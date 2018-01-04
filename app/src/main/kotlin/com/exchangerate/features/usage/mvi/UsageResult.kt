package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviResult
import com.exchangerate.core.structure.MviStatus
import com.exchangerate.features.usage.data.Usage

sealed class UsageResult : MviResult

data class LoadUsageResult(
        val status: MviStatus,
        val data: Usage? = null,
        val error: Throwable? = null) : UsageResult()