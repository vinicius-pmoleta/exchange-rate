package com.exchangerate.features.usage.di

import com.exchangerate.core.di.scope.ActivityScoped
import com.exchangerate.features.usage.presentation.UsageContract
import com.exchangerate.features.usage.presentation.UsagePresenter
import com.exchangerate.features.usage.usecase.LiveFetchUsageUseCase
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
    fun providePresenter(fetchUsageUseCase: LiveFetchUsageUseCase): UsageContract.Action {
        return UsagePresenter(view, fetchUsageUseCase)
    }

}