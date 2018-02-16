package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel

interface ConversionView : MviView<ConversionIntent, ConversionState> {

    fun renderLoading(isLoading: Boolean)

    fun renderCurrencyData(currencies: List<Currency>, fromCurrency: String?, toCurrency: String?)

    fun renderConversionData(conversion: ConversionScreenModel)

    fun renderError()
}