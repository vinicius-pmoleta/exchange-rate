package com.exchangerate.features.conversion.data

import com.exchangerate.core.structure.MviAction

interface ConversionAction : MviAction

class StartLoadingConversionAction : ConversionAction
data class LoadConversionAction(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionAction