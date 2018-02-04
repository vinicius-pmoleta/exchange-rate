package com.exchangerate.features.conversion.presentation.model

data class ConversionScreenModel(
        val rate: String,
        val convertedValue: String
)

data class CurrencyScreenModel(
        val currencies: List<String>
)