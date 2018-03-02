package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.data.HistoryAction
import com.exchangerate.features.history.data.HistoryState
import io.reactivex.Observable

class HistoryRouter(
        private val store: MviStore<HistoryState>,
        private val processor: HistoryProcessor
) : MviRouter<HistoryAction> {

    override fun route(action: HistoryAction): Observable<Unit> {
        return when (action) {
            else -> Observable.just(store.dispatch(action))
        }
    }
}