package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.core.di.ActivityScope
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase
import dagger.Module
import dagger.Provides

@Module
class UsageUseCasesModule {

    @ActivityScope
    @Provides
    fun provideLiveFetchUsageUseCase(
            usageRepository: UsageRepository,
            executionConfiguration: ExecutionConfiguration): FetchUsageLiveUseCase {
        return FetchUsageLiveUseCase(usageRepository, executionConfiguration)
    }

}