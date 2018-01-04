package com.exchangerate.features.usage.usecase

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.core.data.usecase.LiveUseCase
import com.exchangerate.features.usage.data.Usage
import io.reactivex.Flowable

class FetchUsageLiveUseCase(
        val usageRepository: UsageRepository,
        executionConfiguration: ExecutionConfiguration
) : LiveUseCase<Usage, Unit>(executionConfiguration) {

    override fun buildUseCaseObservable(params: Unit?): Flowable<Usage> {
        return Flowable.empty()
    }

}