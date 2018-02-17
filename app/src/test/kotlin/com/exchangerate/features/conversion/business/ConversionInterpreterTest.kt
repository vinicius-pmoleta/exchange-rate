package com.exchangerate.features.conversion.business

import com.exchangerate.features.conversion.data.ApplyConversionAction
import com.exchangerate.features.conversion.data.FetchCurrenciesAction
import com.exchangerate.features.conversion.data.PrepareToApplyConversionAction
import com.exchangerate.features.conversion.data.PrepareToFetchCurrenciesAction
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConversionInterpreterTest {

    private val interpreter = ConversionInterpreter()

    @Test
    fun `verify no translation provided with unknown intent`() {
        val unknownIntent = object : ConversionIntent {}
        val actions = interpreter.translate(unknownIntent)
        assertTrue(actions.isEmpty())
    }

    @Test
    fun `verify conversion intent translated into preparation and conversion actions`() {
        val intent = ApplyConversionIntent("EUR", "USD", 1000F)
        val actions = interpreter.translate(intent)
        assertEquals(2, actions.size)
        assertEquals(PrepareToApplyConversionAction("EUR", "USD", 1000F), actions[0])
        assertEquals(ApplyConversionAction("EUR", "USD", 1000F), actions[1])
    }

    @Test
    fun `verify currencies intent translated into preparation and fetch actions`() {
        val actions = interpreter.translate(LoadCurrenciesIntent())
        assertEquals(2, actions.size)
        assertEquals(PrepareToFetchCurrenciesAction(), actions[0])
        assertEquals(FetchCurrenciesAction(), actions[1])
    }
}