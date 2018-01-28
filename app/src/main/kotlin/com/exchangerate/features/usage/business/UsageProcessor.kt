package com.exchangerate.features.usage.business

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.usage.data.Usage
import io.reactivex.Observable
import io.reactivex.Single

class UsageProcessor(private val repository: UsageRepository) : MviProcessor {

    fun loadUsage(): Observable<Usage> {
        return repository.getUsage()
                .flatMap { response -> Single.just(response.data.usage) }
                .toObservable()
    }
}