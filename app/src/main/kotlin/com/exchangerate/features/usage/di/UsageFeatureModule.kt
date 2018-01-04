package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.di.ActivityScope
import com.exchangerate.features.usage.mvi.UsageInteractor
import com.exchangerate.features.usage.mvi.UsagePresenter
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import dagger.Module
import dagger.Provides

@Module
class UsageFeatureModule {

    @ActivityScope
    @Provides
    fun provideScreenConverter(): UsageScreenConverter {
        return UsageScreenConverter()
    }

    @ActivityScope
    @Provides
    fun providePresenter(interactor: UsageInteractor): UsagePresenter {
        return UsagePresenter(interactor)
    }

    @ActivityScope
    @Provides
    fun provideInteractor(usageRepository: UsageRepository): UsageInteractor {
        return UsageInteractor(usageRepository)
    }

}