package com.exchangerate.features.conversion.presentation.model

import com.exchangerate.core.structure.MviIntent

interface ConversionIntent : MviIntent

class LoadCurrenciesIntent : ConversionIntent

data class ApplyConversionIntent(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionIntent