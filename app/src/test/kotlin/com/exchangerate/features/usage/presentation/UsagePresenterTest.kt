package com.exchangerate.features.usage.presentation

import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.usecase.LiveFetchUsageUseCase
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsagePresenterTest {

    private val view: UsageContract.View = mock()
    private val fetchUsageUseCase: LiveFetchUsageUseCase = mock()

    lateinit var presenter: UsagePresenter

    @Before
    fun setup() {
        presenter = UsagePresenter(view, fetchUsageUseCase)
    }

    @Test
    fun `assert the usage result from use case when executed with success`() {
        val usage: Usage = Usage(10, 100, 90, 2)

        presenter.handleCurrentUsage(usage)

        val argumentCaptor = argumentCaptor<UsageViewModel>()
        verify(view, times(1)).displayCurrentUsage(argumentCaptor.capture())

        val model = argumentCaptor.firstValue
        assertNotNull(model)
        assertEquals(90F, model.remainingPercentage)
        assertEquals(2, model.averagePerDay)
    }

    @Test
    fun `verify that error is displayed when use case is executed with error`() {
        presenter.handleErrorFetchingUsage()
        verify(view, times(1)).displayErrorUsageNotFetched()
    }

}