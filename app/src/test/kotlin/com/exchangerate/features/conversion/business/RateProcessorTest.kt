package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.local.database.entity.RateEntity
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.data.repository.remote.data.RatesResponse
import com.exchangerate.features.conversion.data.RateDao
import com.exchangerate.features.conversion.data.model.ConversionResult
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test

class RateProcessorTest {

    private val repository: RemoteExchangeRepository = mockk(relaxed = true)

    private val rateDao: RateDao = mockk(relaxed = true)

    private val processor = RateProcessor(repository, rateDao)

    @Test
    fun `verify conversion using local rates when available and not expired`() {
        every {
            rateDao.getRateForCurrency("GBP")
        } returns RateEntity("GBP", 0.5F, "EUR", 1)
        every {
            rateDao.getRateForCurrency("USD")
        } returns RateEntity("USD", 1.5F, "EUR", 1)

        val result = processor.applyConversion(
                1000F, "GBP", "USD", RateProcessor.RATE_TTL
        )

        result.test()
                .assertValue(ConversionResult(3000F, 3F))
                .assertOf {
                    verify { repository wasNot Called }
                }
    }

    @Test
    fun `verify conversion using remote rates when not available locally`() {
        val timestamp = 0L

        every {
            rateDao.getRateForCurrency(any())
        } returns null
        every {
            repository.getLatestRates()
        } returns Observable.just(
                RatesResponse(
                        timestamp,
                        "EUR",
                        mapOf(
                                Pair("GBP", 0.5F),
                                Pair("EUR", 1.0F),
                                Pair("USD", 1.5F)
                        )
                )
        )

        val result = processor.applyConversion(
                1000F, "GBP", "USD", timestamp
        )

        result.test()
                .assertValue(ConversionResult(3000F, 3F))
                .assertOf {
                    verify(exactly = 1) {
                        rateDao.insertRates(listOf(
                                RateEntity("GBP", 0.5F, "EUR", timestamp),
                                RateEntity("EUR", 1.0F, "EUR", timestamp),
                                RateEntity("USD", 1.5F, "EUR", timestamp)
                        ))
                    }
                }
    }

    @Test
    fun `verify conversion using remote rates when available locally but are expired`() {
        val timestamp = RateProcessor.RATE_TTL + 1

        every {
            rateDao.getRateForCurrency("GBP")
        } returns RateEntity("GBP", 0.5F, "EUR", 0)
        every {
            rateDao.getRateForCurrency("GBP")
        } returns RateEntity("USD", 1.0F, "EUR", 0)
        every {
            repository.getLatestRates()
        } returns Observable.just(
                RatesResponse(
                        timestamp,
                        "EUR",
                        mapOf(
                                Pair("GBP", 0.5F),
                                Pair("EUR", 1.0F),
                                Pair("USD", 1.5F)
                        )
                )
        )

        val result = processor.applyConversion(
                1000F, "GBP", "USD", RateProcessor.RATE_TTL
        )

        result.test()
                .assertValue(ConversionResult(3000F, 3F))
                .assertOf {
                    verify(exactly = 1) {
                        rateDao.insertRates(listOf(
                                RateEntity("GBP", 0.5F, "EUR", timestamp),
                                RateEntity("EUR", 1.0F, "EUR", timestamp),
                                RateEntity("USD", 1.5F, "EUR", timestamp)
                        ))
                    }
                }
    }
}