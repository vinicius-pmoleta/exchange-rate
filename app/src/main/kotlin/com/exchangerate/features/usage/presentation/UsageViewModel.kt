package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.usage.business.UsageInterpreter
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageIntent
import javax.inject.Inject

class UsageViewModel(
        interpreter: UsageInterpreter,
        store: MviStore<UsageState>
) : MviViewModel<UsageIntent, UsageState>(interpreter, store)

@Suppress("UNCHECKED_CAST")
open class UsageViewModelFactory @Inject constructor(
        private val interpreter: UsageInterpreter,
        private val store: MviStore<UsageState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsageViewModel(interpreter, store) as T
    }
}