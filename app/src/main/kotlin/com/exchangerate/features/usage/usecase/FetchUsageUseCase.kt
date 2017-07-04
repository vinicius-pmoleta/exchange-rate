package com.exchangerate.features.usage.usecase

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.core.data.usecase.UseCase
import com.exchangerate.features.usage.data.Usage
import io.reactivex.Flowable

class FetchUsageUseCase(
        val usageRepository: UsageRepository,
        executionConfiguration: ExecutionConfiguration
) : UseCase<Usage, Unit>(executionConfiguration) {

    override fun buildUseCaseObservable(params: Unit): Flowable<Usage> {
        return usageRepository.getUsage()
                .map({ response -> response.data.usage })
    }

}