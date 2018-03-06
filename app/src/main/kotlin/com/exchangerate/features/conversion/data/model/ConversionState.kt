package com.exchangerate.features.conversion.data.model

import com.exchangerate.core.structure.MviState

data class ConversionData(
        val fromCurrency: String = "",
        val toCurrency: String = "",
        val valueToConvert: Float = 0F,
        val convertedValue: Float = 0F,
        val rate: Float = 0F
)

data class CurrenciesData(
        val currencies: List<Currency> = emptyList()
)

data class ConversionState(
        val isLoading: Boolean = false,
        val conversionData: ConversionData = ConversionData(),
        val currencyData: CurrenciesData = CurrenciesData(),
        val error: Throwable? = null
) : MviState