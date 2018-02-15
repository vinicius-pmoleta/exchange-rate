package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.FailedConversionResultAction
import com.exchangerate.features.conversion.data.PrepareToApplyConversionAction
import com.exchangerate.features.conversion.data.PrepareToFetchCurrenciesAction
import com.exchangerate.features.conversion.data.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.SuccessfulCurrenciesResultAction
import com.exchangerate.features.usage.data.FailedUsageResultAction

class ConversionReducer : MviReducer<ConversionState> {

    override fun reduce(action: MviAction, state: ConversionState): ConversionState {
        return when (action) {
            is PrepareToApplyConversionAction -> state.copy(
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
            is SuccessfulConversionResultAction -> state.copy(
                    isLoading = false,
                    conversionData = state.conversionData.copy(
                            convertedValue = action.conversion.convertedValue,
                            rate = action.conversion.rate
                    )
            )
            is FailedConversionResultAction -> state.copy(
                    isLoading = false,
                    error = action.error
            )
            is PrepareToFetchCurrenciesAction -> state.copy(
                    isLoading = true,
                    currencyData = state.currencyData.copy(
                            isInitialized = false
                    )
            )
            is SuccessfulCurrenciesResultAction -> state.copy(
                    isLoading = false,
                    currencyData = state.currencyData.copy(
                            isInitialized = true,
                            currencies = action.currencies
                    )
            )
            is FailedUsageResultAction -> state.copy(
                    isLoading = false,
                    error = action.error
            )
            else -> state
        }
    }

    override fun initialState(): ConversionState = ConversionState()
}