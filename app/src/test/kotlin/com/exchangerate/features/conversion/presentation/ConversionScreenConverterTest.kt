package com.exchangerate.features.conversion.presentation

import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import org.junit.Assert.assertEquals
import org.junit.Test

class ConversionScreenConverterTest {

    private val screenConverter = ConversionScreenConverter()

    @Test
    fun `verify screen model generated for conversion data exceeding decimal places`() {
        val data = ConversionData(
                convertedValue = 1000.98765F,
                rate = 0.98765F
        )
        val screenModel = screenConverter.prepareForPresentation(data)
        assertEquals(ConversionScreenModel("0.988", "1000.99"), screenModel)
    }

    @Test
    fun `verify screen model generated for conversion data with less decimal places`() {
        val data = ConversionData(
                convertedValue = 1000F,
                rate = 0.9F
        )
        val screenModel = screenConverter.prepareForPresentation(data)
        assertEquals(ConversionScreenModel("0.900", "1000.00"), screenModel)
    }
}