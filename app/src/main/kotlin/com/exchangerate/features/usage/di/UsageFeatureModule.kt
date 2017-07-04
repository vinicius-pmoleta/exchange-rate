package com.exchangerate.features.usage.di

import com.exchangerate.features.usage.usecase.FetchUsageUseCase
import com.exchangerate.core.di.scope.ActivityScoped
import com.exchangerate.features.usage.UsageContract
import com.exchangerate.features.usage.UsagePresenter
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
    fun providePresenter(fetchUsageUseCase: FetchUsageUseCase): UsageContract.Action {
        return UsagePresenter(view, fetchUsageUseCase)
    }

}