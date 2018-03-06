package com.exchangerate.features.history.di

import com.exchangerate.core.data.repository.local.database.ExchangeRateDatabase
import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.history.business.HistoryFilter
import com.exchangerate.features.history.business.HistoryInterpreter
import com.exchangerate.features.history.business.HistoryProcessor
import com.exchangerate.features.history.business.HistoryReducer
import com.exchangerate.features.history.business.HistoryRouter
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.presentation.HistoryRenderer
import com.exchangerate.features.history.presentation.HistoryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HistoryFeatureModule {

    @FeatureScope
    @Provides
    fun provideFilter(store: MviStore<HistoryState>): HistoryFilter {
        return HistoryFilter(store)
    }

    @FeatureScope
    @Provides
    fun provideInterpreter(): HistoryInterpreter {
        return HistoryInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideRouter(store: MviStore<HistoryState>, processor: HistoryProcessor): HistoryRouter {
        return HistoryRouter(store, processor)
    }

    @FeatureScope
    @Provides
    fun provideProcessor(database: ExchangeRateDatabase): HistoryProcessor {
        return HistoryProcessor(database.historyDao())
    }

    @FeatureScope
    @Provides
    fun provideReducer(): HistoryReducer {
        return HistoryReducer()
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: HistoryReducer): MviStore<HistoryState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(filter: HistoryFilter,
                                interpreter: HistoryInterpreter,
                                router: HistoryRouter,
                                store: MviStore<HistoryState>): HistoryViewModelFactory {
        return HistoryViewModelFactory(filter, interpreter, router, store)
    }

    @FeatureScope
    @Provides
    fun provideRenderer(): HistoryRenderer {
        return HistoryRenderer()
    }
}