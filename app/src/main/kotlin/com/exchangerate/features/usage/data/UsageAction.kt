package com.exchangerate.features.usage.data

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviAction

interface UsageAction : MviAction

class PrepareToFetchUsageAction : UsageAction

class FetchUsageAction : UsageAction

data class SuccessfulUsageResultAction(
        val usage: Usage
) : UsageAction

data class FailedUsageResultAction(
        val error: Throwable
) : UsageAction