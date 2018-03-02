package com.exchangerate.features.conversion.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.conversion.business.ConversionFilter
import com.exchangerate.features.conversion.business.ConversionInterpreter
import com.exchangerate.features.conversion.business.ConversionRouter
import com.exchangerate.features.conversion.data.model.ConversionAction
import com.exchangerate.features.conversion.data.model.ConversionState
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import javax.inject.Inject

class ConversionViewModel(
        filter: ConversionFilter,
        interpreter: ConversionInterpreter,
        router: ConversionRouter,
        store: MviStore<ConversionState>
) : MviViewModel<ConversionIntent, ConversionAction, ConversionState>(filter, interpreter, router, store)

@Suppress("UNCHECKED_CAST")
open class ConversionViewModelFactory @Inject constructor(
        private val filter: ConversionFilter,
        private val interpreter: ConversionInterpreter,
        private val router: ConversionRouter,
        private val store: MviStore<ConversionState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConversionViewModel(filter, interpreter, router, store) as T
    }
}