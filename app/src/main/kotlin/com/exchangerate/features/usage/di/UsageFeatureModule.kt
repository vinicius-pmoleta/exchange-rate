package com.exchangerate.features.usage.di

import com.exchangerate.core.di.ActivityScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.mvi.*
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
    fun provideInterpreter(): UsageInterpreter {
        return UsageInterpreter()
    }

    @ActivityScope
    @Provides
    fun provideProcessor(): UsageProcessor {
        return UsageProcessor()
    }

    @ActivityScope
    @Provides
    fun provideReducer(processor: UsageProcessor): UsageReducer {
        return UsageReducer(processor)
    }

    @ActivityScope
    @Provides
    fun provideStore(reducer: UsageReducer): MviStore<UsageState> {
        return MviStore(reducer)
    }

    @ActivityScope
    @Provides
    fun provideViewModelFactory(interpreter: UsageInterpreter, store: MviStore<UsageState>): UsageViewModelFactory {
        return UsageViewModelFactory(interpreter, store)
    }
}