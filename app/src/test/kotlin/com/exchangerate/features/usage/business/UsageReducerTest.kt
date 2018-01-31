package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.features.usage.data.LoadUsageAction
import com.exchangerate.features.usage.data.StartLoadingUsageAction
import com.exchangerate.features.usage.data.UsageAction
import com.exchangerate.features.usage.data.UsageState
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test
import java.io.IOException

class UsageReducerTest {

    private val processor: UsageProcessor = mockk(relaxed = true)

    private val reducer = UsageReducer(processor)

    @Test
    fun `verify old state not changed when unknown action is requested`() {
        val unkknowAction = object : UsageAction {}
        val oldState = UsageState(isLoading = false, error = IOException())
        val stateObservable = reducer.reduce(unkknowAction, oldState)

        stateObservable
                .test()
                .assertValue(oldState)
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun `verify that StartLoadingUsageAction modify state to isLoading = true`() {
        val oldState = UsageState(isLoading = false)
        val stateObservable = reducer.reduce(StartLoadingUsageAction(), oldState)

        stateObservable
                .test()
                .assertValue(UsageState(isLoading = true))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun `verify that LoadUsageAction modify state to have data and isLoading=false if success`() {
        val oldState = UsageState(isLoading = true)
        val usage = Usage(10, 100, 90, 2)
        every { processor.loadUsage() } returns Observable.just(usage)
        val stateObservable = reducer.reduce(LoadUsageAction(), oldState)

        stateObservable
                .test()
                .assertValue(UsageState(isLoading = false, data = usage))
                .assertNoErrors()
                .assertComplete()
    }

    @Test
    fun `verify that LoadUsageAction modify state to have error and isLoading=false if error`() {
        val oldState = UsageState(isLoading = true)
        val error = IOException()
        every { processor.loadUsage() } returns Observable.error(error)
        val stateObservable = reducer.reduce(LoadUsageAction(), oldState)

        stateObservable
                .test()
                .assertValue(UsageState(isLoading = false, error = error))
                .assertNoErrors()
                .assertComplete()
    }

}