package com.exchangerate.features.usage.mvi

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.structure.MviStatus
import com.exchangerate.features.usage.data.UsageResponse
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsageInteractor(private val usageRepository: UsageRepository) {

    fun loadUsageAction(): ObservableTransformer<UsageAction, UsageResult> {
        return ObservableTransformer { actions ->
            actions.flatMap {
                usageRepository.getUsage()
                        .toObservable()
                        .map { response: UsageResponse -> LoadUsageResult(status = MviStatus.SUCCESS, data = response.data.usage) }
                        .publish()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn { error -> LoadUsageResult(status = MviStatus.FAILURE, error = error) }
                        .startWith(LoadUsageResult(status = MviStatus.IN_FLIGHT))
            }

        }
    }

}