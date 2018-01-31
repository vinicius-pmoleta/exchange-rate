package com.exchangerate.features.conversion.di

import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.business.ConversionInterpreter
import com.exchangerate.features.conversion.business.ConversionProcessor
import com.exchangerate.features.conversion.business.ConversionReducer
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.presentation.ConversionRenderer
import com.exchangerate.features.conversion.presentation.ConversionScreenConverter
import com.exchangerate.features.conversion.presentation.ConversionViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ConversionFeatureModule {

    @FeatureScope
    @Provides
    fun provideInterpreter(): ConversionInterpreter {
        return ConversionInterpreter()
    }

    @FeatureScope
    @Provides
    fun provideProcessor(repository: RemoteExchangeRepository): ConversionProcessor {
        return ConversionProcessor(repository)
    }

    @FeatureScope
    @Provides
    fun provideReducer(processor: ConversionProcessor): ConversionReducer {
        return ConversionReducer(processor)
    }

    @FeatureScope
    @Provides
    fun provideStore(reducer: ConversionReducer): MviStore<ConversionState> {
        return MviStore(reducer)
    }

    @FeatureScope
    @Provides
    fun provideViewModelFactory(interpreter: ConversionInterpreter, store: MviStore<ConversionState>): ConversionViewModelFactory {
        return ConversionViewModelFactory(interpreter, store)
    }

    @FeatureScope
    @Provides
    fun provideConverter(): ConversionScreenConverter {
        return ConversionScreenConverter()
    }

    @FeatureScope
    @Provides
    fun provideRenderer(converter: ConversionScreenConverter): ConversionRenderer {
        return ConversionRenderer(converter)
    }
}