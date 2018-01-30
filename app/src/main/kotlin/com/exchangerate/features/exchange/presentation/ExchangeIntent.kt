package com.exchangerate.features.exchange.presentation

import com.exchangerate.core.structure.MviIntent

interface ExchangeIntent : MviIntent

data class LoadExchangeIntent(
        val currencyFrom: String,
        val currencyTo: String,
        val valueToConvert: Float
) : ExchangeIntent