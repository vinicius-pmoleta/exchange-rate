package com.exchangerate.features.conversion.business

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.conversion.data.ConversionResult
import io.reactivex.Observable

class ConversionProcessor(private val repository: RemoteExchangeRepository) : MviProcessor {

    fun applyConversion(value: Float, fromCurrency: String, toCurrency: String): Observable<ConversionResult> {
        return repository.getConversion(value, fromCurrency, toCurrency)
                .map { response -> ConversionResult(response.convertedValue, response.metadata.rate) }
                .toObservable()
    }
}