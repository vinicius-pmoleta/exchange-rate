package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviProcessor
import io.reactivex.Observable

class UsageProcessor(private val repository: RemoteExchangeRepository) : MviProcessor {

    fun loadUsage(): Observable<Usage> {
        return repository.getUsage()
                .map { response -> response.data.usage }
                .toObservable()
    }
}