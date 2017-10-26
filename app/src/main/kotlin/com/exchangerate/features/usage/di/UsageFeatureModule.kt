package com.exchangerate.features.usage.di

import com.exchangerate.core.di.scope.ActivityScoped
import com.exchangerate.features.usage.presentation.UsageContract
import com.exchangerate.features.usage.presentation.UsagePresenter
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase
import dagger.Module
import dagger.Provides

@Module
class UsageFeatureModule(val view: UsageContract.View) {

    @ActivityScoped
    @Provides
    fun provideView(): UsageContract.View {
        return view
    }

    @ActivityScoped
    @Provides
    fun provideScreenConverter(): UsageScreenConverter {
        return UsageScreenConverter()
    }

    @ActivityScoped
    @Provides
    fun providePresenter(fetchUsageUseCase: FetchUsageLiveUseCase,
                         screenConverter: UsageScreenConverter): UsageContract.Action {
        return UsagePresenter(view, fetchUsageUseCase, screenConverter)
    }

}