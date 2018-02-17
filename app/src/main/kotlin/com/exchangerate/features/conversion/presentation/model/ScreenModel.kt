package com.exchangerate.features.conversion.presentation.model

import com.exchangerate.features.conversion.data.Currency

data class ConversionScreenModel(
        val rate: String,
        val convertedValue: String
)

data class CurrencyScreenModel(
        val currencies: List<Currency>,
        val fromCurrency: String,
        val toCurrency: String
)