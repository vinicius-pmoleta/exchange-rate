package com.exchangerate.features.usage.presentation

import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import com.exchangerate.features.usage.presentation.model.UsageScreenModel
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsagePresenterTest {

    private val view: UsageContract.View = mock()
    private val fetchUsageUseCase: FetchUsageLiveUseCase = mock()
    private val screenConverter: UsageScreenConverter = mock()

    private lateinit var presenter: UsagePresenter

    @Before
    fun setup() {
        presenter = UsagePresenter(view, fetchUsageUseCase, screenConverter)
    }

    @Test
    fun `assert the usage result from use case when executed with success`() {
        val usage = Usage(10, 100, 90, 2)
        val screenModel = UsageScreenModel(2, 10F, 90)

        whenever(screenConverter.prepareForPresentation(usage)).thenReturn(screenModel)

        presenter.handleCurrentUsage(usage)
        verify(screenConverter, times(1)).prepareForPresentation(usage)
        verify(view, times(1)).displayCurrentUsage(screenModel)
    }

    @Test
    fun `verify that result is not displayed in screen if has success but is null`() {
        presenter.handleCurrentUsage(null)
        verify(view, never()).displayCurrentUsage(any())
    }

    @Test
    fun `verify that error is displayed when use case is executed with error`() {
        presenter.handleErrorFetchingUsage()
        verify(view, times(1)).displayErrorUsageNotFetched()
    }

}