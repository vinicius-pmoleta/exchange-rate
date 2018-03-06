package com.exchangerate.features.conversion.data.model

import com.exchangerate.core.structure.MviAction

interface ConversionAction : MviAction

data class PrepareToApplyConversionAction(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionAction

data class ApplyConversionAction(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ConversionAction

data class SuccessfulConversionResultAction(
        val conversion: ConversionResult
) : ConversionAction

data class FailedConversionResultAction(
        val error: Throwable
) : ConversionAction

class PrepareToFetchCurrenciesAction : ConversionAction

class FetchCurrenciesAction : ConversionAction

data class SuccessfulCurrenciesResultAction(
        val currencies: List<Currency>
) : ConversionAction

data class FailedCurrenciesResultAction(
        val error: Throwable
) : ConversionAction