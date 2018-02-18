package com.exchangerate.features.usage.presentation

import com.exchangerate.core.common.percent
import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.features.usage.presentation.model.UsageScreenModel

class UsageScreenConverter {

    fun prepareForPresentation(usage: Usage): UsageScreenModel {
        val usedPercentage = "%.2f".format(usage.sent.percent(usage.quota))
        return UsageScreenModel(
                usage.averagePerDay.toString(),
                usedPercentage,
                usage.remaining.toString()
        )
    }
}