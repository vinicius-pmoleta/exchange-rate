package com.exchangerate.features.usage.presentation.model

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.features.usage.presentation.UsageScreenConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class UsageScreenConverterTest {

    private val screenConverter: UsageScreenConverter = UsageScreenConverter()

    @Test
    fun `verify conversion for presentation from usage model`() {
        val usage = Usage(10, 100, 90, 2)
        val screenModel = screenConverter.prepareForPresentation(usage)
        assertEquals("10.00", screenModel.usedPercentage)
        assertEquals("90", screenModel.remainingRequests)
        assertEquals("2", screenModel.averagePerDay)
    }

}