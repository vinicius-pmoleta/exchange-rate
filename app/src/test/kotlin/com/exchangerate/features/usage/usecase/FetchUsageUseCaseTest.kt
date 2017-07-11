package com.exchangerate.features.usage.usecase

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.features.usage.data.Data
import com.exchangerate.features.usage.data.Usage
import com.exchangerate.features.usage.data.UsageResponse
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchUsageUseCaseTest {

    private val usageRepository: UsageRepository = mock()
    private val executionConfiguration: ExecutionConfiguration = mock()

    private lateinit var useCase: FetchUsageUseCase

    @Before
    fun setup() {
        useCase = FetchUsageUseCase(usageRepository, executionConfiguration)
    }

    @Test
    fun `verify that usage is extracted from valid response`() {
        val usage = Usage(10, 100, 90, 1)
        val data = Data("active", usage)
        val response = UsageResponse(200, data)

        given(usageRepository.getUsage()).willReturn(Flowable.just(response))

        val testSubscriber = useCase.buildUseCaseObservable().test()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(usage)
        testSubscriber.assertComplete()
    }

}