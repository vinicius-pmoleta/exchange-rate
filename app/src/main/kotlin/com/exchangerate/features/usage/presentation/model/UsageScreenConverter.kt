package com.exchangerate.features.usage.presentation.model

import com.exchangerate.core.common.percent
import com.exchangerate.features.usage.data.Usage

class UsageScreenConverter {

    fun prepareForPresentation(usage: Usage): UsageScreenModel {
        val usedPercentage = usage.sent.percent(usage.quota)
        return UsageScreenModel(usage.averagePerDay, usedPercentage, usage.remaining)
    }
}