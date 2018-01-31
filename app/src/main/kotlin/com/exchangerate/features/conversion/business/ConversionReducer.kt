package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.LoadConversionAction
import com.exchangerate.features.conversion.data.StartLoadingConversionAction
import io.reactivex.Observable

class ConversionReducer(private val processor: ConversionProcessor) : MviReducer<ConversionState> {

    override fun reduce(action: MviAction, state: ConversionState): Observable<ConversionState> {
        return when (action) {
            is StartLoadingConversionAction -> Observable.just(
                    state.copy(
                            isLoading = true,
                            data = state.data.copy(
                                    fromCurrency = action.currencyFrom,
                                    toCurrency = action.currencyTo,
                                    valueToConvert = action.valueToConvert
                            )
                    )
            )
            is LoadConversionAction -> processor
                    .applyConversion(action.valueToConvert, action.currencyFrom, action.currencyTo)
                    .map { conversion ->
                        state.copy(
                                isLoading = false,
                                data = state.data.copy(
                                        convertedValue = conversion.convertedValue,
                                        rate = conversion.rate
                                )
                        )
                    }
                    .onErrorReturn { error ->
                        state.copy(isLoading = false, error = error)
                    }
            else -> Observable.just(state)
        }
    }

    override fun initialState(): ConversionState = ConversionState()
}