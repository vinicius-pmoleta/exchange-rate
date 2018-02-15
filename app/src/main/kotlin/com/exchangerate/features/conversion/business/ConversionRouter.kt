package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.*
import io.reactivex.Observable

class ConversionRouter(
        private val store: MviStore<ConversionState>,
        private val processor: ConversionProcessor
) : MviRouter<ConversionAction> {

    override fun route(action: ConversionAction) : Observable<Unit> {
        return when (action) {
            is ApplyConversionAction -> processor
                    .applyConversion(action.valueToConvert, action.currencyFrom, action.currencyTo)
                    .map { conversion ->
                        store.dispatch(SuccessfulConversionResultAction(conversion))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedConversionResultAction(error))
                    }
            is FetchCurrenciesAction -> processor
                    .loadCurrencies()
                    .map { currencies ->
                        store.dispatch(SuccessfulCurrenciesResultAction(currencies))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedCurrenciesResultAction(error))
                    }
            else -> Observable.just(store.dispatch(action))
        }
    }
}