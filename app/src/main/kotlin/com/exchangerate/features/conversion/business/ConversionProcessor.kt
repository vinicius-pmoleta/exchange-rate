package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.conversion.data.ConversionDao
import com.exchangerate.features.conversion.data.ConversionResult
import com.exchangerate.features.conversion.data.Currency
import io.reactivex.Flowable
import io.reactivex.Observable

class ConversionProcessor(
        private val repository: RemoteExchangeRepository,
        private val conversionDao: ConversionDao
) : MviProcessor {

    fun applyConversion(value: Float, fromCurrency: String, toCurrency: String): Observable<ConversionResult> {
        return repository.getLatestRates()
                .map { response -> response.rates }
                .filter { rates -> rates[fromCurrency] != null && rates[toCurrency] != null }
                .map { rates -> rates[toCurrency]!!.div(rates[fromCurrency]!!) }
                .map { conversionRate -> ConversionResult(value * conversionRate, conversionRate) }
                .toObservable()
    }

    fun loadCurrencies(): Observable<List<Currency>> {
        return Observable.just(conversionDao.getAllCurrencies())
                .flatMap { fromDatabase ->
                    if (!fromDatabase.isEmpty()) {
                        convertCurrenciesFromEntityToModel(fromDatabase)
                    } else {
                        fetchRemoteCurrenciesAndStore()
                                .flatMap { fromRemote ->
                                    convertCurrenciesFromEntityToModel(fromRemote)
                                }
                    }
                }
    }

    private fun fetchRemoteCurrenciesAndStore(): Observable<List<CurrencyEntity>> {
        return repository.getCurrencies()
                .flatMap { response ->
                    Observable.fromIterable(response.entries)
                            .collectInto(mutableListOf<CurrencyEntity>(), { list, entry ->
                                list.add(CurrencyEntity(entry.key, entry.value))
                            })
                            .toObservable()
                            .doOnNext { entities -> conversionDao.insertCurrencies(entities) }
                }
    }

    private fun convertCurrenciesFromEntityToModel(entities: List<CurrencyEntity>): Observable<List<Currency>> {
        return Flowable.fromIterable(entities)
                .collectInto(mutableListOf<Currency>(), { list, entity ->
                    list.add(Currency(entity.code, entity.name))
                })
                .map { mutable -> mutable.toList() }
                .toObservable()
    }
}