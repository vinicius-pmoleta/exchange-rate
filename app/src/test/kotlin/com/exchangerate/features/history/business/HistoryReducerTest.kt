package com.exchangerate.features.history.business

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.features.history.data.model.FailedHistoryResultAction
import com.exchangerate.features.history.data.model.HistoryAction
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.data.model.PrepareToLoadHistoryAction
import com.exchangerate.features.history.data.model.SuccessfulHistoryResultAction
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class HistoryReducerTest {

    private val reducer = HistoryReducer()

    @Test
    fun `verify old state not changed when unknown action is requested`() {
        val unknownAction = object : HistoryAction {}
        val oldState = HistoryState(isLoading = false, error = IOException())
        val newState = reducer.reduce(unknownAction, oldState)

        assertEquals(oldState, newState)
    }

    @Test
    fun `verify that prepare to load modify state to start loading`() {
        val oldState = HistoryState(isLoading = false)
        val newState = reducer.reduce(PrepareToLoadHistoryAction(), oldState)

        assertEquals(HistoryState(isLoading = true), newState)
    }

    @Test
    fun `verify that successful result modify state to have data and stop loading`() {
        val oldState = HistoryState(isLoading = true)
        val dataSource: DataSource.Factory<Int, HistoryEntity> = mockk(relaxed = true)
        val newState = reducer.reduce(SuccessfulHistoryResultAction(dataSource), oldState)

        assertEquals(HistoryState(isLoading = false, data = dataSource), newState)
    }

    @Test
    fun `verify that failed result modify state to have error and stop loading`() {
        val oldState = HistoryState(isLoading = true)
        val error = IOException()
        val newState = reducer.reduce(FailedHistoryResultAction(error), oldState)

        assertEquals(HistoryState(isLoading = false, error = error), newState)
    }

}