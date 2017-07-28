package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.exchangerate.core.structure.BasePresenter
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.LiveFetchUsageUseCase
import org.reactivestreams.Subscription

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: LiveFetchUsageUseCase) : BasePresenter(), UsageContract.Action {

    override fun loadCurrentUsage(liveUsageViewModel: LiveUsageViewModel) {
        if (liveUsageViewModel.source != null) {
            handleCurrentUsage(liveUsageViewModel.source?.value)
            return
        }

        val liveUsage: LiveData<Usage> = fetchUsageUseCase.execute(
                onSubscribe = { handleSubscription(it) },
                onError = { handleErrorFetchingUsage() })

        liveUsageViewModel.source = liveUsage
        liveUsage.observe(view.provideLifecycleOwner(), Observer { usage -> handleCurrentUsage(usage) })
    }

    fun handleSubscription(subscription: Subscription) {
        // TODO
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