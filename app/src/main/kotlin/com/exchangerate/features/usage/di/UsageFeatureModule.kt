package com.exchangerate.features.usage.di

import com.exchangerate.core.di.ActivityScope
import com.exchangerate.features.usage.presentation.UsageContract
import com.exchangerate.features.usage.presentation.UsagePresenter
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import com.exchangerate.features.usage.usecase.FetchUsageLiveUseCase
import dagger.Module
import dagger.Provides

@Module
class UsageFeatureModule(val view: UsageContract.View) {

    @ActivityScope
    @Provides
    fun provideView(): UsageContract.View {
        return view
    }

    @ActivityScope
    @Provides
    fun provideScreenConverter(): UsageScreenConverter {
        return UsageScreenConverter()
    }

    @ActivityScope
    @Provides
    fun providePresenter(fetchUsageUseCase: FetchUsageLiveUseCase,
                         screenConverter: UsageScreenConverter): UsageContract.Action {
        return UsagePresenter(view, fetchUsageUseCase, screenConverter)
    }

}