package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.UsageRepository
import com.exchangerate.core.data.usecase.FetchUsageUseCase
import com.exchangerate.core.di.scope.ActivityScoped
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