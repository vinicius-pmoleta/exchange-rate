package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel

interface ConversionView : MviView<ConversionIntent, ConversionState> {

    fun renderData(data: ConversionScreenModel)

    fun renderError()
}