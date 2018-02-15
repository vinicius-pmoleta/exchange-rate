package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.conversion.data.*
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent

class ConversionInterpreter : MviIntentInterpreter<ConversionIntent, ConversionAction> {

    override fun translate(intent: ConversionIntent): List<ConversionAction> {
        return when (intent) {
            is ApplyConversionIntent -> listOf(
                    PrepareToApplyConversionAction(intent.currencyFrom, intent.currencyTo, intent.valueToConvert),
                    ApplyConversionAction(intent.currencyFrom, intent.currencyTo, intent.valueToConvert)
            )
            is LoadCurrenciesIntent -> listOf(
                    PrepareToFetchCurrenciesAction(),
                    FetchCurrenciesAction()
            )
            else -> emptyList()
        }
    }
}