package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviRouter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.model.ApplyConversionAction
import com.exchangerate.features.conversion.data.model.ConversionAction
import com.exchangerate.features.conversion.data.model.ConversionState
import com.exchangerate.features.conversion.data.model.FailedConversionResultAction
import com.exchangerate.features.conversion.data.model.FailedCurrenciesResultAction
import com.exchangerate.features.conversion.data.model.FetchCurrenciesAction
import com.exchangerate.features.conversion.data.model.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.model.SuccessfulCurrenciesResultAction
import io.reactivex.Observable

class ConversionRouter(
        private val store: MviStore<ConversionState>,
        private val rateProcessor: RateProcessor,
        private val currenciesProcessor: CurrenciesProcessor
) : MviRouter<ConversionAction> {

    override fun route(action: ConversionAction): Observable<Unit> {
        return when (action) {
            is ApplyConversionAction -> rateProcessor
                    .applyConversion(action.valueToConvert, action.currencyFrom,
                            action.currencyTo, System.currentTimeMillis() / 1000)
                    .doOnSubscribe { store.next(action) }
                    .map { conversion ->
                        store.dispatch(SuccessfulConversionResultAction(conversion))
                    }
                    .onErrorReturn { error ->
                        store.dispatch(FailedConversionResultAction(error))
                    }
            is FetchCurrenciesAction -> currenciesProcessor.loadCurrencies()
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