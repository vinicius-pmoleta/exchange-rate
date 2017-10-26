package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.Observer
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase

class UsagePresenter(val view: UsageContract.View,
                     private val fetchUsageUseCase: FetchUsageLiveUseCase,
                     private val screenConverter: UsageScreenConverter) : UsageContract.Action {

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
            view.displayCurrentUsage(screenConverter.prepareForPresentation(usage))
        }
    }

    fun handleErrorFetchingUsage() {
        view.displayErrorUsageNotFetched()
    }

}