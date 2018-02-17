package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.data.SuccessfulUsageResultAction
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageInitialIntent
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UsageFilterTest {

    private val store: MviStore<UsageState> = mockk(relaxed = true)

    private val filter = UsageFilter(store)

    @Test
    fun `shouldn't filter if usage is not available on state`() {
        val result = filter.apply(UsageInitialIntent(), UsageState())

        assertTrue(result)
        verify(exactly = 0) { store.dispatch(SuccessfulUsageResultAction(any())) }
    }

    @Test
    fun `should filter if usage is available on state`() {
        val data = Usage(10, 100, 90, 2)
        val result = filter.apply(UsageInitialIntent(), UsageState(data = data))

        assertFalse(result)
        verify(exactly = 1) { store.dispatch(SuccessfulUsageResultAction(data)) }
    }

}