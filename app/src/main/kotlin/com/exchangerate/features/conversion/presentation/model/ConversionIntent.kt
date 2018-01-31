package com.exchangerate.features.conversion.presentation.model

import com.exchangerate.core.structure.MviIntent

interface ConversionIntent : MviIntent

data class ApplyConversionIntent(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionIntent