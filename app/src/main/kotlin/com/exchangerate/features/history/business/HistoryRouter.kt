package com.exchangerate.features.history.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.data.model.FailedHistoryResultAction
import com.exchangerate.features.history.data.model.HistoryAction
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.data.model.LoadHistoryAction
import com.exchangerate.features.history.data.model.SuccessfulHistoryResultAction
import io.reactivex.Observable

class HistoryRouter(
        private val store: MviStore<HistoryState>,
        private val processor: HistoryProcessor
) : MviRouter<HistoryAction> {

    override fun route(action: HistoryAction): Observable<Unit> {
        return when (action) {
            is LoadHistoryAction -> processor.loadHistoryOrderByDate()
                    .doOnSubscribe { store.next(action) }
                    .map { pagedHistory ->
                        store.dispatch(SuccessfulHistoryResultAction(pagedHistory))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedHistoryResultAction(error))
                    }
            else -> Observable.just(store.dispatch(action))
        }
    }
}