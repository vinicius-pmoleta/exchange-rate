package com.exchangerate.features.usage.usecase

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.features.usage.data.Usage
import io.reactivex.Flowable

class FetchUsageUseCase(val usageRepository: UsageRepository) {

    fun execute(): Flowable<Usage> {
        return usageRepository.getUsage()
                .map({ response -> response.data.usage })
    }

}