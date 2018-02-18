package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.data.repository.remote.data.RatesResponse
import com.exchangerate.features.conversion.data.ConversionResult
import com.exchangerate.features.conversion.data.Currency
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

class ConversionProcessorTest {

    private val repository: RemoteExchangeRepository = mockk(relaxed = true)

    private val processor = ConversionProcessor(repository)

    @Test
    fun `verify currencies map converted to models`() {
        every {
            repository.getCurrencies()
        } returns Observable.just(mapOf(
                Pair("EUR", "Euro"),
                Pair("USD", "Dollar")
        ))

        val result = processor.loadCurrencies()

        result.test()
                .assertValue(listOf(
                        Currency("EUR", "Euro"),
                        Currency("USD", "Dollar")
                ))
    }

    @Test
    fun `verify conversion empty if from currency is missing`() {
        every {
            repository.getLatestRates()
        } returns Single.just(
                RatesResponse(1, "EUR", mapOf(
                        Pair("EUR", 1F),
                        Pair("USD", 1.5F)
                ))
        )

        val result = processor.applyConversion(1000F, "GBP", "USD")

        result.test().assertNoValues()
    }

    @Test
    fun `verify conversion empty if to currency is missing`() {
        every {
            repository.getLatestRates()
        } returns Single.just(
                RatesResponse(1, "EUR", mapOf(
                        Pair("EUR", 1F),
                        Pair("GBP", 0.5F)
                ))
        )

        val result = processor.applyConversion(1000F, "GBP", "USD")

        result.test().assertNoValues()
    }

    @Test
    fun `verify conversion from currencies rate`() {
        every {
            repository.getLatestRates()
        } returns Single.just(
                RatesResponse(1, "EUR", mapOf(
                        Pair("EUR", 1F),
                        Pair("USD", 1.5F),
                        Pair("GBP", 0.5F)
                ))
        )

        val result = processor.applyConversion(1000F, "GBP", "USD")

        result.test()
                .assertValue(ConversionResult(3000F, 3F))
    }
}