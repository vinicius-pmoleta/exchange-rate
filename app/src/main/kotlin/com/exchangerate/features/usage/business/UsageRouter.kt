package com.exchangerate.features.usage.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.data.*
import io.reactivex.Observable

class UsageRouter(
        private val store: MviStore<UsageState>,
        private val processor: UsageProcessor
) : MviRouter<UsageAction> {

    override fun route(action: UsageAction): Observable<Unit> {
        return when (action) {
            is FetchUsageAction -> processor.loadUsage()
                    .map { usage ->
                        store.dispatch(SuccessfulUsageResultAction(usage))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedUsageResultAction(error))
                    }
            else -> Observable.just(store.dispatch(action))
        }
    }
}