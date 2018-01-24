package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.usage.data.Usage
import io.reactivex.Observable

class UsageProcessor : MviProcessor {

    fun loadUsage() : Observable<Usage> {
        return Observable.empty()
    }
}