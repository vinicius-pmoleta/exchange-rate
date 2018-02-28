package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.local.database.entity.RateEntity
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

    companion object {
        val RATE_TTL: Long = 60 * 60
    }

    fun applyConversion(value: Float,
                        fromCurrency: String,
                        toCurrency: String,
                        nowTimestamp: Long): Observable<ConversionResult> {
        return Observable.just(
                Pair(
                        conversionDao.getRateForCurrency(fromCurrency),
                        conversionDao.getRateForCurrency(toCurrency)
                ))
                .flatMap { localRates ->
                    if (isStoredRateValid(localRates.first, nowTimestamp) &&
                            isStoredRateValid(localRates.second, nowTimestamp)) {
                        Observable.just(Pair(localRates.first!!.rate, localRates.second!!.rate))
                    } else {
                        fetchRemoteRatesAndStore(fromCurrency, toCurrency)
                    }
                }
                .map { rates -> rates.second.div(rates.first) }
                .map { conversionRate -> ConversionResult(value * conversionRate, conversionRate) }
    }

    private fun fetchRemoteRatesAndStore(fromCurrency: String,
                                         toCurrency: String): Observable<Pair<Float, Float>> {
        return repository.getLatestRates()
                .flatMap { response ->
                    Observable.fromIterable(response.rates.entries)
                            .collectInto(mutableListOf<RateEntity>(), { list, entry ->
                                list.add(RateEntity(entry.key, entry.value, response.base, response.timestamp))
                            })
                            .toObservable()
                            .doOnNext { entities -> conversionDao.insertRates(entities) }
                            .map { _ -> Pair(response.rates[fromCurrency]!!, response.rates[toCurrency]!!) }
                }

    }

    private fun isStoredRateValid(entity: RateEntity?, nowTimestamp: Long): Boolean {
        return entity?.let {
            it.timestamp + RATE_TTL > nowTimestamp
        } ?: false
    }

    fun loadCurrencies(): Observable<List<Currency>> {
        return Observable.just(conversionDao.getAllCurrencies())
                .flatMap { localCurrencies ->
                    if (!localCurrencies.isEmpty()) {
                        convertCurrenciesFromEntityToModel(localCurrencies)
                    } else {
                        fetchRemoteCurrenciesAndStore()
                                .flatMap { remoteCurrencies ->
                                    convertCurrenciesFromEntityToModel(remoteCurrencies)
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