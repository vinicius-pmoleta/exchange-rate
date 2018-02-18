package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.CurrenciesData
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.data.SuccessfulCurrenciesResultAction
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConversionFilterTest {

    private val store: MviStore<ConversionState> = mockk(relaxed = true)

    private val filter = ConversionFilter(store)

    @Test
    fun `shouldn't filter when currencies is empty`() {
        val state = ConversionState()
        val result = filter.apply(LoadCurrenciesIntent(), state)

        assertTrue(result)
        verify { store wasNot Called }
    }

    @Test
    fun `should filter when currencies list is not empty`() {
        val data = listOf(Currency("USD", "Dollar"), Currency("EUR", "Euro"))

        val state = ConversionState(currencyData = CurrenciesData(data))
        val result = filter.apply(LoadCurrenciesIntent(), state)

        assertFalse(result)
        verify(exactly = 1) { store.dispatch(SuccessfulCurrenciesResultAction(data)) }
    }
}