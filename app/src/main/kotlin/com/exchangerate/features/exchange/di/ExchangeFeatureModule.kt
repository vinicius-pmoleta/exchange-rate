package com.exchangerate.features.exchange.di

import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.exchange.business.ExchangeInterpreter
import com.exchangerate.features.exchange.business.ExchangeProcessor
import com.exchangerate.features.exchange.business.ExchangeReducer
import com.exchangerate.features.exchange.data.ExchangeState
import com.exchangerate.features.exchange.presentation.ExchangeRenderer
import com.exchangerate.features.exchange.presentation.ExchangeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ExchangeFeatureModule {

    @FeatureScope
    @Provides
    fun provideInterpreter(): ExchangeInterpreter {
        return ExchangeInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideProcessor(): ExchangeProcessor {
        return ExchangeProcessor()
    }

    @FeatureScope
    @Provides
    fun provideReducer(processor: ExchangeProcessor): ExchangeReducer {
        return ExchangeReducer(processor)
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: ExchangeReducer): MviStore<ExchangeState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(interpreter: ExchangeInterpreter, store: MviStore<ExchangeState>): ExchangeViewModelFactory {
        return ExchangeViewModelFactory(interpreter, store)
    }

    @FeatureScope
    @Provides
    fun provideRenderer(): ExchangeRenderer {
        return ExchangeRenderer()
    }
}