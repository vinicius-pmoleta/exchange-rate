package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.exchangerate.core.structure.BasePresenter
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageLiveUseCase) : BasePresenter(), UsageContract.Action {

    override fun loadCurrentUsage() {
        val usageDataHolder = view.provideUsageDataHolder()
        usageDataHolder.data?.let {
            handleCurrentUsage(usageDataHolder.data?.value)
            return
        }

        val liveUsage: LiveData<Usage> = fetchUsageUseCase.execute(
                onError = { handleErrorFetchingUsage() })

        usageDataHolder.data = liveUsage
        liveUsage.observe(view.provideLifecycleOwner(), Observer { usage -> handleCurrentUsage(usage) })
    }

    fun handleCurrentUsage(usage: Usage?) {
        usage?.let {
            val remainingPercentage = 100 * (usage.remaining.toFloat() / usage.quota.toFloat())
            val model = UsageViewModel(usage.averagePerDay, remainingPercentage)
            view.displayCurrentUsage(model)
        }
    }

    fun handleErrorFetchingUsage() {
        view.displayErrorUsageNotFetched()
    }

}