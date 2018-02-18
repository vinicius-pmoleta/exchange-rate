package com.exchangerate.features.conversion.business

import com.exchangerate.features.conversion.data.ConversionAction
import com.exchangerate.features.conversion.data.ConversionData
import com.exchangerate.features.conversion.data.ConversionResult
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.CurrenciesData
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.data.FailedConversionResultAction
import com.exchangerate.features.conversion.data.FailedCurrenciesResultAction
import com.exchangerate.features.conversion.data.PrepareToApplyConversionAction
import com.exchangerate.features.conversion.data.PrepareToFetchCurrenciesAction
import com.exchangerate.features.conversion.data.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.SuccessfulCurrenciesResultAction
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class ConversionReducerTest {

    private val reducer = ConversionReducer()

    @Test
    fun `verify old state not changed when unknown action is requested`() {
        val unknownAction = object : ConversionAction {}
        val oldState = ConversionState(isLoading = false, error = IOException())
        val newState = reducer.reduce(unknownAction, oldState)

        assertEquals(oldState, newState)
    }

    @Test
    fun `verify that preparation to convert set the currencies and value on state`() {
        val action = PrepareToApplyConversionAction("EUR", "USD", 1000F)

        val oldState = ConversionState()
        val newState = reducer.reduce(action, oldState)

        assertEquals(ConversionState(
                isLoading = true,
                conversionData = ConversionData(
                        fromCurrency = "EUR", toCurrency = "USD", valueToConvert = 1000F
                )
        ), newState)
    }

    @Test
    fun `verify that successful conversion update converted value and rate on state`() {
        val action = SuccessfulConversionResultAction(ConversionResult(1500F, 1.5F))

        val oldState = ConversionState(
                isLoading = true,
                conversionData = ConversionData(
                        fromCurrency = "EUR", toCurrency = "USD", valueToConvert = 1000F
                )
        )
        val newState = reducer.reduce(action, oldState)

        assertEquals(ConversionState(
                isLoading = false,
                conversionData = ConversionData(
                        fromCurrency = "EUR", toCurrency = "USD", valueToConvert = 1000F,
                        convertedValue = 1500F, rate = 1.5F
                )
        ), newState)
    }

    @Test
    fun `verify that failed conversion update error on state`() {
        val exception = IOException()

        val oldState = ConversionState(isLoading = true)
        val newState = reducer.reduce(FailedConversionResultAction(exception), oldState)

        assertEquals(ConversionState(isLoading = false, error = exception), newState)
    }

    @Test
    fun `verify that preparation to fetch currencies update loading on state`() {
        val oldState = ConversionState(isLoading = false)
        val newState = reducer.reduce(PrepareToFetchCurrenciesAction(), oldState)

        assertEquals(ConversionState(isLoading = true), newState)
    }

    @Test
    fun `verify that successful currencies fetch update currencies on state`() {
        val data = listOf(Currency("USD", "Dollar"), Currency("EUR", "Euro"))

        val oldState = ConversionState(isLoading = true)
        val newState = reducer.reduce(SuccessfulCurrenciesResultAction(data), oldState)

        assertEquals(ConversionState(
                isLoading = false,
                currencyData = CurrenciesData(data)
        ), newState)
    }

    @Test
    fun `verify that failed currencies fetching update error on state`() {
        val exception = IOException()

        val oldState = ConversionState(isLoading = true)
        val newState = reducer.reduce(FailedCurrenciesResultAction(exception), oldState)

        assertEquals(ConversionState(isLoading = false, error = exception), newState)
    }
}