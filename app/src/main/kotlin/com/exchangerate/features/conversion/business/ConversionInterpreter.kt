package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.conversion.data.LoadConversionAction
import com.exchangerate.features.conversion.data.StartLoadingConversionAction
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent

class ConversionInterpreter : MviIntentInterpreter<ConversionIntent> {

    override fun translate(intent: ConversionIntent): List<MviAction> {
        return when (intent) {
            is ApplyConversionIntent -> listOf(
                    StartLoadingConversionAction(intent.currencyFrom, intent.currencyTo, intent.valueToConvert),
                    LoadConversionAction(intent.currencyFrom, intent.currencyTo, intent.valueToConvert)
            )
            else -> emptyList()
        }
    }
}