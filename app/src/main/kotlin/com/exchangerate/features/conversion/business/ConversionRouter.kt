package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.ApplyConversionAction
import com.exchangerate.features.conversion.data.ConversionAction
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.FailedConversionResultAction
import com.exchangerate.features.conversion.data.FailedCurrenciesResultAction
import com.exchangerate.features.conversion.data.FetchCurrenciesAction
import com.exchangerate.features.conversion.data.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.SuccessfulCurrenciesResultAction
import io.reactivex.Observable

class ConversionRouter(
        private val store: MviStore<ConversionState>,
        private val processor: ConversionProcessor
) : MviRouter<ConversionAction> {

    override fun route(action: ConversionAction): Observable<Unit> {
        return when (action) {
            is ApplyConversionAction -> processor
                    .applyConversion(action.valueToConvert, action.currencyFrom,
                            action.currencyTo, System.currentTimeMillis() / 1000)
                    .doOnSubscribe { store.next(action) }
                    .map { conversion ->
                        store.dispatch(SuccessfulConversionResultAction(conversion))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedConversionResultAction(error))
                    }
            is FetchCurrenciesAction -> processor.loadCurrencies()
                    .doOnSubscribe { store.next(action) }
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