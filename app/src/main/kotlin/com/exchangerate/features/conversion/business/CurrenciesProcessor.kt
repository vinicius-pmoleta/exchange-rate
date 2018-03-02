package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.local.database.entity.CurrencyEntity
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.conversion.data.CurrenciesDao
import com.exchangerate.features.conversion.data.model.Currency
import io.reactivex.Flowable
import io.reactivex.Observable

class CurrenciesProcessor(
        private val repository: RemoteExchangeRepository,
        private val currenciesDao: CurrenciesDao
) : MviProcessor {

    fun loadCurrencies(): Observable<List<Currency>> {
        return Observable.just(currenciesDao.getAllCurrencies())
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
                            .doOnNext { entities -> currenciesDao.insertCurrencies(entities) }
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