package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import com.exchangerate.features.conversion.presentation.model.CurrencyScreenModel

interface ConversionView : MviView<ConversionIntent, ConversionState> {

    fun renderLoading(isLoading: Boolean)

    fun renderCurrencyData(data: CurrencyScreenModel)

    fun renderConversionData(data: ConversionScreenModel)

    fun renderError()
}