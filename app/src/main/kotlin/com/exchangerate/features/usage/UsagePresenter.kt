package com.exchangerate.features.usage

import android.util.Log
import com.exchangerate.features.usage.usecase.FetchUsageUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageUseCase) : UsageContract.Action {

    override fun loadCurrentUsage() {
        fetchUsageUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { usage -> Log.d("TEST", usage.toString()) },
                        { error -> Log.e("TEST", "Error", error) }
                )
    }

}