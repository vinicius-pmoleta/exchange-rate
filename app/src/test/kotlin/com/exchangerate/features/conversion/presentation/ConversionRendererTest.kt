package com.exchangerate.features.conversion.presentation

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ConversionRendererTest {

    private val view: ConversionView = mockk(relaxed = true)

    private val screenConverter: ConversionScreenConverter = mockk(relaxed = true)

    private val renderer = ConversionRenderer(screenConverter)

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }
}