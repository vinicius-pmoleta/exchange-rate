package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.conversion.data.*
import io.reactivex.Observable

class ConversionReducer(private val processor: ConversionProcessor) : MviReducer<ConversionState> {

    override fun reduce(action: MviAction, state: ConversionState): Observable<ConversionState> {
        return when (action) {
            is StartLoadingConversionAction -> Observable.just(
                    state.copy(
                            isLoading = true,
                            conversionData = state.conversionData.copy(
                                    fromCurrency = action.currencyFrom,
                                    toCurrency = action.currencyTo,
                                    valueToConvert = action.valueToConvert,
                                    convertedValue = 0F,
                                    rate = 0F
                            ),
                            currencyData = state.currencyData.copy(
                                    isInitialized = true
                            )
                    )
            )
            is LoadConversionAction -> processor
                    .applyConversion(action.valueToConvert, action.currencyFrom, action.currencyTo)
                    .map { conversion ->
                        state.copy(
                                isLoading = false,
                                conversionData = state.conversionData.copy(
                                        convertedValue = conversion.convertedValue,
                                        rate = conversion.rate
                                )
                        )
                    }
                    .onErrorReturn { error ->
                        state.copy(isLoading = false, error = error)
                    }
            is StartLoadingCurrenciesAction -> Observable.just(
                    state.copy(
                            isLoading = true,
                            currencyData = state.currencyData.copy(
                                    isInitialized = false
                            )
                    )
            )
            is LoadCurrenciesAction -> processor
                    .loadCurrencies()
                    .map { currencies ->
                        state.copy(
                                isLoading = false,
                                currencyData = state.currencyData.copy(
                                        currencies = currencies
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