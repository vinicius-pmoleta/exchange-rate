package com.exchangerate.features.usage.di

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.usage.business.UsageFilter
import com.exchangerate.features.usage.business.UsageInterpreter
import com.exchangerate.features.usage.business.UsageProcessor
import com.exchangerate.features.usage.business.UsageReducer
import com.exchangerate.features.usage.business.UsageRouter
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.UsageRenderer
import com.exchangerate.features.usage.presentation.UsageViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UsageFeatureModule {

    @FeatureScope
    @Provides
    fun provideFilter(store: MviStore<UsageState>): UsageFilter {
        return UsageFilter(store)
    }

    @FeatureScope
    @Provides
    fun provideInterpreter(): UsageInterpreter {
        return UsageInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideRouter(store: MviStore<UsageState>, processor: UsageProcessor): UsageRouter {
        return UsageRouter(store, processor)
    }

    @FeatureScope
    @Provides
    fun provideProcessor(repository: RemoteExchangeRepository): UsageProcessor {
        return UsageProcessor(repository)
    }

    @FeatureScope
    @Provides
    fun provideReducer(): UsageReducer {
        return UsageReducer()
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: UsageReducer): MviStore<UsageState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(filter: UsageFilter,
                                interpreter: UsageInterpreter,
                                router: UsageRouter,
                                store: MviStore<UsageState>): UsageViewModelFactory {
        return UsageViewModelFactory(filter, interpreter, router, store)
    }

    @FeatureScope
    @Provides
    fun provideRenderer(): UsageRenderer {
        return UsageRenderer()
    }
}