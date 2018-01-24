package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import io.reactivex.Observable

class UsageReducer(private val processor: UsageProcessor) : MviReducer<UsageState> {

    override fun reduce(action: MviAction, state: UsageState): Observable<UsageState> {
        return when (action) {
            is StartLoadingUsageAction -> Observable.just(state.copy(isLoading = true))
            is LoadUsageAction -> processor.loadUsage()
                    .map { usage ->
                        state.copy(isLoading = false, data = usage)
                    }
                    .onErrorReturn { error ->
                        state.copy(isLoading = false, error = error)
                    }
            else -> Observable.just(state)
        }
    }

    override fun initialState(): UsageState = UsageState()
}