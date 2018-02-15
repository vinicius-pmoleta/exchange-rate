package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.features.usage.data.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class UsageReducerTest {

    private val reducer = UsageReducer()

    @Test
    fun `verify old state not changed when unknown action is requested`() {
        val unkknowAction = object : UsageAction {}
        val oldState = UsageState(isLoading = false, error = IOException())
        val newState = reducer.reduce(unkknowAction, oldState)

        assertEquals(oldState, newState)
    }

    @Test
    fun `verify that PrepareToFetchUsageAction modify state to isLoading = true`() {
        val oldState = UsageState(isLoading = false)
        val newState = reducer.reduce(PrepareToFetchUsageAction(), oldState)

        assertEquals(UsageState(isLoading = true), newState)
    }

    @Test
    fun `verify that LoadUsageAction modify state to have data and isLoading=false if success`() {
        val oldState = UsageState(isLoading = true)
        val usage = Usage(10, 100, 90, 2)
        val newState = reducer.reduce(SuccessfulUsageResultAction(usage), oldState)

        assertEquals(UsageState(isLoading = false, data = usage), newState)
    }

    @Test
    fun `verify that LoadUsageAction modify state to have error and isLoading=false if error`() {
        val oldState = UsageState(isLoading = true)
        val error = IOException()
        val newState = reducer.reduce(FailedUsageResultAction(error), oldState)

        assertEquals(UsageState(isLoading = false, error = error), newState)
    }

}