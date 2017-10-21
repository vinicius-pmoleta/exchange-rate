package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.Observer
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageLiveUseCase) : UsageContract.Action {

    override fun loadCurrentUsage() {
        val holder = view.provideUsageDataHolder()
        if (LiveDataOperator.isDataAvailable(holder.result)) {
            handleCurrentUsage(holder.result?.data?.value)
            return
        }

        val result = fetchUsageUseCase.executeLive(onSubscribe = holder::addSubscription)
        result.observe(
                view.provideLifecycleOwner(),
                Observer { usage -> handleCurrentUsage(usage) },
                Observer { handleErrorFetchingUsage() }
        )
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