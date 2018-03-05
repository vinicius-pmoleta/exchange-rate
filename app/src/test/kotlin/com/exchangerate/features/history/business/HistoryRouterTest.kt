package com.exchangerate.features.history.business

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.data.model.FailedHistoryResultAction
import com.exchangerate.features.history.data.model.HistoryAction
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.data.model.LoadHistoryAction
import com.exchangerate.features.history.data.model.SuccessfulHistoryResultAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.io.IOException

class HistoryRouterTest {

    private val store: MviStore<HistoryState> = mockk(relaxed = true)

    private val processor: HistoryProcessor = mockk(relaxed = true)

    private val router = HistoryRouter(store, processor)

    @Test
    fun `verify action forwarded to store if no action on router`() {
        val unknownAction = object : HistoryAction {}
        val result = router.route(unknownAction)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(unknownAction) }
                )
    }

    @Test
    fun `verify processor called to load history and forwarded result to store`() {
        val dataSource: DataSource.Factory<Int, HistoryEntity> = mockk(relaxed = true)
        every { processor.loadHistoryOrderByDate() } returns Observable.just(dataSource)

        val action = LoadHistoryAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(SuccessfulHistoryResultAction(dataSource)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }

    @Test
    fun `verify processor called to load history and forwarded error to store`() {
        val error = IOException()
        every { processor.loadHistoryOrderByDate() } returns Observable.error(error)

        val action = LoadHistoryAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(FailedHistoryResultAction(error)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }
}