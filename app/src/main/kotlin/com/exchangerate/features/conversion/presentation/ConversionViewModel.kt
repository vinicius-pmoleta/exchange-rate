package com.exchangerate.features.conversion.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.conversion.business.ConversionInterpreter
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import javax.inject.Inject

class ConversionViewModel(
        interpreter: ConversionInterpreter,
        store: MviStore<ConversionState>
) : MviViewModel<ConversionIntent, ConversionState>(interpreter, store)

@Suppress("UNCHECKED_CAST")
open class ConversionViewModelFactory @Inject constructor(
        private val interpreter: ConversionInterpreter,
        private val store: MviStore<ConversionState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ConversionViewModel(interpreter, store) as T
    }
}