package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.data.FailedUsageResultAction
import com.exchangerate.features.usage.data.FetchUsageAction
import com.exchangerate.features.usage.data.SuccessfulUsageResultAction
import com.exchangerate.features.usage.data.UsageAction
import com.exchangerate.features.usage.data.UsageState
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
        val unknownAction = object : UsageAction {}
        val result = router.route(unknownAction)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(unknownAction) }
                )
    }

    @Test
    fun `verify processor called to fetch usage and forwarded result to store`() {
        val usage = Usage(10, 100, 90, 1)
        every { processor.loadUsage() } returns Observable.just(usage)

        val action = FetchUsageAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(SuccessfulUsageResultAction(usage)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }

    @Test
    fun `verify processor called to fetch usage and forwarded error to store`() {
        val error = IOException()
        every { processor.loadUsage() } returns Observable.error(error)

        val action = FetchUsageAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(FailedUsageResultAction(error)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }
}