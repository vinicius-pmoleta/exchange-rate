package com.exchangerate.features.usage

import android.util.Log
import com.exchangerate.core.data.usecase.UseCaseSubscriber
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.usecase.FetchUsageUseCase
import io.reactivex.disposables.CompositeDisposable

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageUseCase) : UsageContract.Action {

    private val disposables = CompositeDisposable()

    override fun loadCurrentUsage() {
        disposables.add(fetchUsageUseCase.execute(UsageSubscriber(), Unit))
    }

    fun onStop() {
        disposables.dispose()
    }

}

class UsageSubscriber : UseCaseSubscriber<Usage>() {

    override fun onNext(next: Usage) {
        Log.d("TEST", next.toString())
    }

    override fun onError(error: Throwable?) {
        Log.e("TEST", "Error", error)
    }

}