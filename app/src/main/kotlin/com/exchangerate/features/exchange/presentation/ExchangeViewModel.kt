package com.exchangerate.features.exchange.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.exchange.business.ExchangeInterpreter
import com.exchangerate.features.exchange.data.ExchangeState
import javax.inject.Inject

class ExchangeViewModel(
        interpreter: ExchangeInterpreter,
        store: MviStore<ExchangeState>
) : MviViewModel<ExchangeIntent, ExchangeState>(interpreter, store)

@Suppress("UNCHECKED_CAST")
open class ExchangeViewModelFactory @Inject constructor(
        private val interpreter: ExchangeInterpreter,
        private val store: MviStore<ExchangeState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExchangeViewModel(interpreter, store) as T
    }
}