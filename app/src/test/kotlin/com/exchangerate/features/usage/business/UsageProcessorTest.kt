package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.data.repository.remote.data.Data
import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.data.repository.remote.data.UsageResponse
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class UsageProcessorTest {

    private val repository: RemoteExchangeRepository = mockk(relaxed = true)

    private val processor = UsageProcessor(repository)

    @Test
    fun `verify usage is extracted from response if success`() {
        val usage = Usage(10, 100, 90, 2)
        val response = UsageResponse(200, data = Data("active", usage))
        every { repository.getUsage() } returns Single.just(response)

        processor.loadUsage()
                .test()
                .assertValue(usage)
                .assertNoErrors()
                .assertComplete()
    }
}