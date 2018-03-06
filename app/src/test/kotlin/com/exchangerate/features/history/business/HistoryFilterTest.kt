package com.exchangerate.features.history.business

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.data.model.SuccessfulHistoryResultAction
import com.exchangerate.features.history.presentation.model.HistoryInitialIntent
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

class HistoryFilterTest {

    private val store: MviStore<HistoryState> = mockk(relaxed = true)

    private val filter = HistoryFilter(store)

    @Test
    fun `shouldn't filter if history data source is not available on state`() {
        val result = filter.apply(HistoryInitialIntent(), HistoryState())

        assertTrue(result)
        verify { store wasNot Called }
    }

    @Test
    fun `should filter if history data source is available on state`() {
        val dataSource: DataSource.Factory<Int, HistoryEntity> = mockk(relaxed = true)
        val result = filter.apply(HistoryInitialIntent(), HistoryState(data = dataSource))

        Assert.assertFalse(result)
        verify(exactly = 1) { store.dispatch(SuccessfulHistoryResultAction(dataSource)) }
    }
}