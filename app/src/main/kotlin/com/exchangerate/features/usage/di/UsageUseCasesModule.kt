package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.di.scope.ActivityScoped
import com.exchangerate.features.usage.usecase.FetchUsageUseCase
import dagger.Module
import dagger.Provides

@Module
class UsageUseCasesModule {

    @ActivityScoped
    @Provides
    fun provideFetchUsageUseCase(usageRepository: UsageRepository): FetchUsageUseCase {
        return FetchUsageUseCase(usageRepository)
    }

}