package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.conversion.data.ConversionResult
import com.exchangerate.features.conversion.data.Currency
import io.reactivex.Observable

class ConversionProcessor(private val repository: RemoteExchangeRepository) : MviProcessor {

    fun applyConversion(value: Float, fromCurrency: String, toCurrency: String): Observable<ConversionResult> {
        return repository.getLatestRates()
                .map { response -> response.rates }
                .filter { rates -> rates[fromCurrency] != null && rates[toCurrency] != null }
                .map { rates -> rates[toCurrency]!!.div(rates[fromCurrency]!!) }
                .map { conversionRate -> ConversionResult(value * conversionRate, conversionRate) }
                .toObservable()

    }

    fun loadCurrencies(): Observable<List<Currency>> {
        return repository.getCurrencies()
                .flatMap { response -> Observable.fromIterable(response.entries) }
                .map { currency -> Currency(currency.key, currency.value) }
                .toList()
                .toObservable()
    }
}