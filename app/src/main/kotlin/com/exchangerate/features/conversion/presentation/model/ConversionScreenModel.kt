package com.exchangerate.features.conversion.presentation.model

data class ConversionScreenModel(
        val isLoading: Boolean = false,
        val rate: String = "",
        val convertedValue: String = "",
        val currencies: List<String> = emptyList(),
        val fromCurrencyPosition: Int = -1,
        val toCurrencyPosition: Int = -1
)