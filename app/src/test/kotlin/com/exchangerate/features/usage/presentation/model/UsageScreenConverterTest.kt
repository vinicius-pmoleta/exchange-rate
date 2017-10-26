package com.exchangerate.features.usage.presentation.model

import com.exchangerate.features.usage.data.Usage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsageScreenConverterTest {

    private val screenConverter: UsageScreenConverter = UsageScreenConverter()

    @Test
    fun `verify conversion for presentation from usage model`() {
        val usage = Usage(10, 100, 90, 2)
        val screenModel = screenConverter.prepareForPresentation(usage)
        assertEquals(10F, screenModel.usedPercentage)
        assertEquals(90, screenModel.remainingRequests)
        assertEquals(2, screenModel.averagePerDay)
    }

}