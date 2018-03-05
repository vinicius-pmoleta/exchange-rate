package com.exchangerate.features.history.presentation

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryScreenModel
import io.mockk.Called
import io.mockk.CapturingSlot
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class HistoryRendererTest {

    private val view: HistoryView = mockk(relaxed = true)

    private val renderer = HistoryRenderer()

    @Test
    fun `verify nothing is triggered in the view when state is unavailable`() {
        renderer.render(null, view)
        verify { view wasNot Called }
    }

    @Test
    fun `verify data is rendered`() {
        val dataSource: DataSource.Factory<Int, HistoryEntity> = mockk(relaxed = true)
        val state = HistoryState(isLoading = false, data = dataSource)

        val screenModelSlot = CapturingSlot<HistoryScreenModel>()
        renderer.render(state, view)
        verify(exactly = 1) { view.renderData(capture(screenModelSlot), any()) }

        Assert.assertEquals(
                HistoryScreenModel(false, dataSource),
                screenModelSlot.captured
        )
    }

    @Test
    fun `verify error isn't rendered when not available`() {
        val state = HistoryState()
        renderer.render(state, view)
        verify(exactly = 0) { view.renderError() }
    }

    @Test
    fun `verify error is rendered when available`() {
        val state = HistoryState(isLoading = false, error = IOException())
        renderer.render(state, view)
        verify(exactly = 1) { view.renderError() }
    }
}