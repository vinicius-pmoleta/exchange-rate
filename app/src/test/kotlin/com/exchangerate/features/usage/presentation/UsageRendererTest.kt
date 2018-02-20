package com.exchangerate.features.usage.presentation

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageScreenModel
import io.mockk.Called
import io.mockk.CapturingSlot
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class UsageRendererTest {

    private val view: UsageView = mockk(relaxed = true)

    private val renderer = UsageRenderer()

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }

    @Test
    fun `verify data is rendered`() {
        val usage = Usage(10, 100, 90, 2)
        val state = UsageState(isLoading = false, data = usage)

        val screenModelSlot = CapturingSlot<UsageScreenModel>()
        renderer.render(state, view)
        verify(exactly = 1) { view.renderData(capture(screenModelSlot)) }

        assertEquals(
                UsageScreenModel(false, "2", "10.00", "90"),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify error isn't rendered when not available`() {
        val state = UsageState()
        renderer.render(state, view)
        verify(exactly = 0) { view.renderError() }
    }

    @Test
    fun `verify error is rendered when available`() {
        val state = UsageState(isLoading = false, error = IOException())
        renderer.render(state, view)
        verify(exactly = 1) { view.renderError() }
    }
}