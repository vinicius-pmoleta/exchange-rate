package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.UsageRepository
import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.business.*
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.UsageRenderer
import com.exchangerate.features.usage.presentation.UsageViewModelFactory
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import dagger.Module
import dagger.Provides

@Module
class UsageFeatureModule {

    @FeatureScope
    @Provides
    fun provideScreenConverter(): UsageScreenConverter {
        return UsageScreenConverter()
    }

    @FeatureScope
    @Provides
    fun provideInterpreter(): UsageInterpreter {
        return UsageInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideProcessor(repository: UsageRepository): UsageProcessor {
        return UsageProcessor(repository)
    }

    @FeatureScope
    @Provides
    fun provideReducer(processor: UsageProcessor): UsageReducer {
        return UsageReducer(processor)
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: UsageReducer): MviStore<UsageState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(interpreter: UsageInterpreter, store: MviStore<UsageState>): UsageViewModelFactory {
        return UsageViewModelFactory(interpreter, store)
    }

    @FeatureScope
    @Provides
    fun provideRenderer(): UsageRenderer {
        return UsageRenderer()
    }
}