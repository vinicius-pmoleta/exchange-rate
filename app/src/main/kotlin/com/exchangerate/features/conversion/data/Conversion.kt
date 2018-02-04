package com.exchangerate.features.conversion.data

data class ConversionResult(
        val convertedValue: Float,
        val rate: Float
)

data class Currency(
        val code: String,
        val name: String
)