package com.exchangerate.features.conversion.presentation

import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.CurrenciesData
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import io.mockk.Called
import io.mockk.CapturingSlot
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class ConversionRendererTest {

    private val view: ConversionView = mockk(relaxed = true)

    private val renderer = ConversionRenderer()

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }

    @Test
    fun `verify currencies are rendered without previous selection`() {
        val currencies = listOf(Currency("USD", "Dollar"), Currency("EUR", "Euro"))
        val state = ConversionState(
                currencyData = CurrenciesData(currencies)
        )

        val screenModelSlot = CapturingSlot<ConversionScreenModel>()
        renderer.render(state, view)

        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }
        assertEquals(
                ConversionScreenModel(
                        currencies = listOf("USD", "EUR"),
                        fromCurrencyPosition = -1,
                        toCurrencyPosition = -1,
                        convertedValue = "0.00",
                        rate = "0.000"
                ),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify currencies are rendered with previous selection`() {
        val currencies = listOf(
                Currency("USD", "Dollar"),
                Currency("EUR", "Euro"),
                Currency("BRL", "Real")
        )
        val state = ConversionState(
                conversionData = ConversionData(
                        fromCurrency = "EUR",
                        toCurrency = "BRL"
                ),
                currencyData = CurrenciesData(currencies)
        )

        val screenModelSlot = CapturingSlot<ConversionScreenModel>()
        renderer.render(state, view)

        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }
        assertEquals(
                ConversionScreenModel(
                        currencies = listOf("USD", "EUR", "BRL"),
                        fromCurrencyPosition = 1,
                        toCurrencyPosition = 2,
                        convertedValue = "0.00",
                        rate = "0.000"
                ),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify conversion result rendered with data exceeding decimal places`() {
        val state = ConversionState(
                conversionData = ConversionData(
                        convertedValue = 1000.98765F,
                        rate = 0.98765F
                )
        )

        val screenModelSlot = CapturingSlot<ConversionScreenModel>()
        renderer.render(state, view)

        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }
        assertEquals(
                ConversionScreenModel(
                        convertedValue = "1000.99",
                        rate = "0.988"
                ),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify conversion result rendered with data lacking decimal places`() {
        val state = ConversionState(
                conversionData = ConversionData(
                        convertedValue = 1000F,
                        rate = 0.9F
                )
        )

        val screenModelSlot = CapturingSlot<ConversionScreenModel>()
        renderer.render(state, view)

        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }
        assertEquals(
                ConversionScreenModel(
                        convertedValue = "1000.00",
                        rate = "0.900"
                ),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify full state rendered`() {
        val currencies = listOf(
                Currency("USD", "Dollar"),
                Currency("EUR", "Euro"),
                Currency("BRL", "Real")
        )
        val state = ConversionState(
                isLoading = true,
                conversionData = ConversionData(
                        fromCurrency = "EUR",
                        toCurrency = "BRL",
                        valueToConvert = 1000F,
                        convertedValue = 4000F,
                        rate = 4F
                ),
                currencyData = CurrenciesData(currencies)
        )

        val screenModelSlot = CapturingSlot<ConversionScreenModel>()
        renderer.render(state, view)
        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }
        assertEquals(
                ConversionScreenModel(
                        isLoading = true,
                        currencies = listOf("USD", "EUR", "BRL"),
                        fromCurrencyPosition = 1,
                        toCurrencyPosition = 2,
                        convertedValue = "4000.00",
                        rate = "4.000"
                ),
                screenModelSlot.captured
        )
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