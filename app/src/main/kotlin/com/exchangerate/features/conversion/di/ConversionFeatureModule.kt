package com.exchangerate.features.conversion.di

import com.exchangerate.core.data.repository.local.database.ExchangeRateDatabase
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.business.ConversionFilter
import com.exchangerate.features.conversion.business.ConversionInterpreter
import com.exchangerate.features.conversion.business.RateProcessor
import com.exchangerate.features.conversion.business.ConversionReducer
import com.exchangerate.features.conversion.business.ConversionRouter
import com.exchangerate.features.conversion.business.CurrenciesProcessor
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.presentation.ConversionRenderer
import com.exchangerate.features.conversion.presentation.ConversionViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ConversionFeatureModule {

    @FeatureScope
    @Provides
    fun provideFilter(store: MviStore<ConversionState>): ConversionFilter {
        return ConversionFilter(store)
    }

    @FeatureScope
    @Provides
    fun provideInterpreter(): ConversionInterpreter {
        return ConversionInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideRouter(store: MviStore<ConversionState>,
                      rateProcessor: RateProcessor,
                      currenciesProcessor: CurrenciesProcessor): ConversionRouter {
        return ConversionRouter(store, rateProcessor, currenciesProcessor)
    }

    @FeatureScope
    @Provides
    fun provideConversionProcessor(repository: RemoteExchangeRepository,
                                   database: ExchangeRateDatabase): RateProcessor {
        return RateProcessor(repository, database.rateDao())
    }

    @FeatureScope
    @Provides
    fun provideCurrenciesProcessor(repository: RemoteExchangeRepository,
                                   database: ExchangeRateDatabase): CurrenciesProcessor {
        return CurrenciesProcessor(repository, database.currenciesDao())
    }

    @FeatureScope
    @Provides
    fun provideReducer(): ConversionReducer {
        return ConversionReducer()
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: ConversionReducer): MviStore<ConversionState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(filter: ConversionFilter,
                                interpreter: ConversionInterpreter,
                                router: ConversionRouter,
                                store: MviStore<ConversionState>): ConversionViewModelFactory {
        return ConversionViewModelFactory(filter, interpreter, router, store)
    }

    @FeatureScope
    @Provides
    fun provideRenderer(): ConversionRenderer {
        return ConversionRenderer()
    }
}