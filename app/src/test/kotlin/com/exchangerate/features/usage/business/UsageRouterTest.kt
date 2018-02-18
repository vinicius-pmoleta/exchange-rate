package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.data.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.io.IOException

class UsageRouterTest {

    private val store: MviStore<UsageState> = mockk(relaxed = true)

    private val processor: UsageProcessor = mockk(relaxed = true)

    private val router = UsageRouter(store, processor)

    @Test
    fun `verify action forwarded to store if no action on router`() {
        val unkknowAction = object : UsageAction {}
        val result = router.route(unkknowAction)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(unkknowAction) }
                )
    }

    @Test
    fun `verify processor called to fetch usage and forwarded result to store`() {
        val usage = Usage(10, 100, 90, 1)
        every { processor.loadUsage() } returns Observable.just(usage)

        val result = router.route(FetchUsageAction())

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(SuccessfulUsageResultAction(usage)) }
                )
    }

    @Test
    fun `verify processor called to fetch usage and forwarded error to store`() {
        val error = IOException()
        every { processor.loadUsage() } returns Observable.error(error)

        val result = router.route(FetchUsageAction())

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(FailedUsageResultAction(error)) }
                )
    }
}