package com.exchangerate.features.usage.presentation

import android.util.Log
import com.exchangerate.core.data.usecase.UseCaseSubscriber
import com.exchangerate.core.structure.BasePresenter
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.FetchUsageUseCase

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageUseCase) : BasePresenter(), UsageContract.Action {

    override fun loadCurrentUsage() {
        addDisposable(fetchUsageUseCase.execute(UsageSubscriber(view), Unit))
    }

    override fun releaseResources() {
        disposeAll()
    }
}

class UsageSubscriber(val view: UsageContract.View) : UseCaseSubscriber<Usage>() {

    override fun onNext(next: Usage) {
        Log.d("TEST", next.toString())
        val remainingPercentage = 100 * (next.remaining.toFloat() / next.quota.toFloat())
        val model = UsageViewModel(next.averagePerDay, remainingPercentage)
        view.displayCurrentUsage(model)
    }

    override fun onError(error: Throwable?) {
        Log.e("TEST", "Error", error)
    }

}