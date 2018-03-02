package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviReducer
import com.exchangerate.features.conversion.data.model.ConversionState
import com.exchangerate.features.conversion.data.model.FailedConversionResultAction
import com.exchangerate.features.conversion.data.model.FailedCurrenciesResultAction
import com.exchangerate.features.conversion.data.model.PrepareToApplyConversionAction
import com.exchangerate.features.conversion.data.model.PrepareToFetchCurrenciesAction
import com.exchangerate.features.conversion.data.model.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.model.SuccessfulCurrenciesResultAction

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
                    isLoading = true
            )
            is SuccessfulCurrenciesResultAction -> state.copy(
                    isLoading = false,
                    currencyData = state.currencyData.copy(
                            currencies = action.currencies
                    )
            )
            is FailedCurrenciesResultAction -> state.copy(
                    isLoading = false,
                    error = action.error
            )
            else -> state
        }
    }

    override fun initialState(): ConversionState = ConversionState()
}