package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.ApplyConversionAction
import com.exchangerate.features.conversion.data.ConversionAction
import com.exchangerate.features.conversion.data.ConversionResult
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.data.FailedConversionResultAction
import com.exchangerate.features.conversion.data.FailedCurrenciesResultAction
import com.exchangerate.features.conversion.data.FetchCurrenciesAction
import com.exchangerate.features.conversion.data.SuccessfulConversionResultAction
import com.exchangerate.features.conversion.data.SuccessfulCurrenciesResultAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import java.io.IOException

class ConversionRouterTest {

    private val store: MviStore<ConversionState> = mockk(relaxed = true)

    private val rateProcessor: RateProcessor = mockk(relaxed = true)

    private val currenciesProcessor: CurrenciesProcessor = mockk(relaxed = true)

    private val router = ConversionRouter(store, rateProcessor, currenciesProcessor)

    @Test
    fun `verify unknown action forwarded to store dispatch`() {
        val unknownAction = object : ConversionAction {}
        val result = router.route(unknownAction)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(unknownAction) }
                )
    }

    @Test
    fun `verify successful conversion dispatch result`() {
        val conversion = ConversionResult(1500F, 1.5F)
        every {
            rateProcessor.applyConversion(1000F, "EUR", "USD", any())
        } returns Observable.just(conversion)

        val action = ApplyConversionAction("EUR", "USD", 1000F)
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(SuccessfulConversionResultAction(conversion)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }

    @Test
    fun `verify failed conversion dispatch error`() {
        val exception = IOException()
        every {
            rateProcessor.applyConversion(1000F, "EUR", "USD", any())
        } returns Observable.error(exception)

        val action = ApplyConversionAction("EUR", "USD", 1000F)
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(FailedConversionResultAction(exception)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }

    @Test
    fun `verify successful currencies fetch dispatch result`() {
        val currencies = listOf(Currency("USD", "Dollar"), Currency("EUR", "Euro"))
        every { currenciesProcessor.loadCurrencies() } returns Observable.just(currencies)

        val action = FetchCurrenciesAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(SuccessfulCurrenciesResultAction(currencies)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }

    @Test
    fun `verify failed currencies fetch dispatch error`() {
        val exception = IOException()
        every { currenciesProcessor.loadCurrencies() } returns Observable.error(exception)

        val action = FetchCurrenciesAction()
        val result = router.route(action)

        result.test()
                .assertValue(
                        verify(exactly = 1) { store.dispatch(FailedCurrenciesResultAction(exception)) }
                )
                .assertOf {
                    verify(exactly = 1) { store.next(action) }
                }
    }
}