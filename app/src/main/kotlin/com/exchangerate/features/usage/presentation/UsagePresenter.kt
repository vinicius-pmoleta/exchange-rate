package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.BasePresenter
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.FetchUsageUseCase

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageUseCase) : BasePresenter(), UsageContract.Action {

    override fun loadCurrentUsage() {
        addDisposable(fetchUsageUseCase.execute(
                onNext = { handleCurrentUsage(it) },
                onError = { handleErrorFetchingUsage() }))
    }

    fun handleCurrentUsage(usage: Usage) {
        val remainingPercentage = 100 * (usage.remaining.toFloat() / usage.quota.toFloat())
        val model = UsageViewModel(usage.averagePerDay, remainingPercentage)
        view.displayCurrentUsage(model)
    }

    fun handleErrorFetchingUsage() {
        view.displayErrorUsageNotFetched()
    }

}