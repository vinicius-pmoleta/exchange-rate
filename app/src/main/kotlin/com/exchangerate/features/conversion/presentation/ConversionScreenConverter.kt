package com.exchangerate.features.conversion.presentation

import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel

class ConversionScreenConverter {

    fun prepareForPresentation(data: ConversionData): ConversionScreenModel {
        val rate = "%.3f".format(data.rate)
        val value = "%.2f".format(data.convertedValue)
        return ConversionScreenModel(rate, value)
    }
}