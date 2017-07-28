package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.core.di.scope.ActivityScoped
import com.exchangerate.features.usage.usecase.LiveFetchUsageUseCase
import dagger.Module
import dagger.Provides

@Module
class UsageUseCasesModule {

    @ActivityScoped
    @Provides
    fun provideLiveFetchUsageUseCase(usageRepository: UsageRepository, executionConfiguration: ExecutionConfiguration): LiveFetchUsageUseCase {
        return LiveFetchUsageUseCase(usageRepository, executionConfiguration)
    }

}