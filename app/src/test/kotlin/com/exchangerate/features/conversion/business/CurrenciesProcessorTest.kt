package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.features.conversion.data.CurrenciesDao
import com.exchangerate.features.conversion.data.Currency
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test

class CurrenciesProcessorTest {

    private val repository: RemoteExchangeRepository = mockk(relaxed = true)

    private val currenciesDao: CurrenciesDao = mockk(relaxed = true)

    private val processor = CurrenciesProcessor(repository, currenciesDao)

    @Test
    fun `verify currencies are fetched from remote repository when not available locally`() {
        every { currenciesDao.getAllCurrencies() } returns emptyList()
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
                .assertOf {
                    verify(exactly = 1) {
                        currenciesDao.insertCurrencies(listOf(
                                CurrencyEntity("EUR", "Euro"),
                                CurrencyEntity("USD", "Dollar")
                        ))
                    }
                }
    }

    @Test
    fun `verify currencies are fetched from local database when available`() {
        every {
            currenciesDao.getAllCurrencies()
        } returns listOf(
                CurrencyEntity("EUR", "Euro"),
                CurrencyEntity("USD", "Dollar")
        )
        every { repository.getCurrencies() } returns Observable.empty()

        val result = processor.loadCurrencies()
        result.test()
                .assertValue(listOf(
                        Currency("EUR", "Euro"),
                        Currency("USD", "Dollar")
                ))
    }
}