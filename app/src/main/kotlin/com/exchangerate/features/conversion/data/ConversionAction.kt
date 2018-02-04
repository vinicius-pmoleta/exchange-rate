package com.exchangerate.features.conversion.data

import com.exchangerate.core.structure.MviAction

interface ConversionAction : MviAction

data class StartLoadingConversionAction(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionAction

data class LoadConversionAction(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionAction

class StartLoadingCurrenciesAction : ConversionAction

class LoadCurrenciesAction : ConversionAction