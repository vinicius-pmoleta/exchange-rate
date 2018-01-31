package com.exchangerate.features.usage.presentation

import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageScreenModel
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.io.IOException

class UsageRendererTest {

    private val view: UsageView = mockk(relaxed = true)

    private val screenConverter: UsageScreenConverter = mockk(relaxed = true)

    private val renderer = UsageRenderer(screenConverter)

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }

    @Test
    fun `verify loading is rendered`() {
        val state = UsageState(isLoading = true)

        renderer.render(state, view)
        verify(exactly = 1) { view.renderLoading(true) }
    }

    @Test
    fun `verify data is rendered`() {
        val usage = Usage(10, 100, 90, 2)
        val screenUsage = UsageScreenModel(2, 10f, 90)

        every { screenConverter.prepareForPresentation(usage) } returns screenUsage
        val state = UsageState(isLoading = false, data = usage)

        renderer.render(state, view)
        verify(exactly = 1) { view.renderLoading(false) }
        verify(exactly = 1) { view.renderData(screenUsage) }
    }

    @Test
    fun `verify error is rendered`() {
        val state = UsageState(isLoading = false, error = IOException())

        renderer.render(state, view)
        verify(exactly = 1) { view.renderLoading(false) }
        verify(exactly = 1) { view.renderError() }
    }

}