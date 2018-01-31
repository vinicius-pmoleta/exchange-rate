package com.exchangerate.features.conversion.presentation

import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel

class ConversionScreenConverter {

    fun prepareForPresentation(data: ConversionData): ConversionScreenModel {
        return ConversionScreenModel()
    }
}