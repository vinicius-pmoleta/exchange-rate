package com.exchangerate.features.conversion.data

import com.exchangerate.core.structure.MviState

data class ConversionData(
        val fromCurrency: String = "",
        val toCurrency: String = "",
        val valueToConvert: Float = 0F,
        val convertedValue: Float = 0F,
        val rate: Float = 0F
)

data class ConversionState(
        val isLoading: Boolean = false,
        val data: ConversionData = ConversionData(),
        val error: Throwable? = null
) : MviState