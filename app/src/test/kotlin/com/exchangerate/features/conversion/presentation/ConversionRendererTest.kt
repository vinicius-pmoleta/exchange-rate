package com.exchangerate.features.conversion.presentation

import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.CurrenciesData
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import com.exchangerate.features.conversion.presentation.model.CurrencyScreenModel
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.io.IOException

class ConversionRendererTest {

    private val view: ConversionView = mockk(relaxed = true)

    private val screenConverter: ConversionScreenConverter = mockk(relaxed = true)

    private val renderer = ConversionRenderer(screenConverter)

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }

    @Test
    fun `verify loading is rendered`() {
        val state = ConversionState(isLoading = true)
        renderer.render(state, view)
        verify(exactly = 1) { view.renderLoading(true) }
    }

    @Test
    fun `verify currencies are't rendered when empty`() {
        val state = ConversionState()
        renderer.render(state, view)
        verify(exactly = 0) { view.renderCurrencyData(any()) }
    }

    @Test
    fun `verify currencies are rendered when not empty`() {
        val currencies = listOf(Currency("USD", "Dollar"), Currency("EUR", "Euro"))
        val state = ConversionState(
                conversionData = ConversionData(
                        fromCurrency = "EUR",
                        toCurrency = "USD",
                        valueToConvert = 1000F
                ),
                currencyData = CurrenciesData(currencies)
        )
        renderer.render(state, view)

        verify(exactly = 1) { view.renderCurrencyData(CurrencyScreenModel(currencies, "EUR", "USD")) }
    }

    @Test
    fun `verify conversion result rendered`() {
        val state = ConversionState(
                conversionData = ConversionData(
                        fromCurrency = "EUR",
                        toCurrency = "USD",
                        valueToConvert = 1000F,
                        convertedValue = 1500F,
                        rate = 1.5F
                )
        )

        val screenModel = ConversionScreenModel("1.500", "1500,00")
        every { screenConverter.prepareForPresentation(state.conversionData) } returns screenModel

        renderer.render(state, view)
        verify(exactly = 1) { view.renderConversionData(screenModel) }
    }

    @Test
    fun `verify error not rendered when not available`() {
        val state = ConversionState()
        renderer.render(state, view)
        verify(exactly = 0) { view.renderError() }
    }

    @Test
    fun `verify error rendered when available`() {
        val state = ConversionState(error = IOException())
        renderer.render(state, view)
        verify(exactly = 1) { view.renderError() }
    }
}