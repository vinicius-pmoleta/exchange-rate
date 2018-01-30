package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.conversion.data.ConversionState
import io.reactivex.Observable

class ConversionReducer(private val processor: ConversionProcessor) : MviReducer<ConversionState> {

    override fun reduce(action: MviAction, state: ConversionState): Observable<ConversionState> {
        return Observable.empty()
    }

    override fun initialState(): ConversionState = ConversionState()
}