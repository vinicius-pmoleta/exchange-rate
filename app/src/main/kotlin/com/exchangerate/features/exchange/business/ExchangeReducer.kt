package com.exchangerate.features.exchange.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.exchange.data.ExchangeState
import io.reactivex.Observable

class ExchangeReducer(private val processor: ExchangeProcessor) : MviReducer<ExchangeState> {

    override fun reduce(action: MviAction, state: ExchangeState): Observable<ExchangeState> {
        return Observable.empty()
    }

    override fun initialState(): ExchangeState = ExchangeState()
}