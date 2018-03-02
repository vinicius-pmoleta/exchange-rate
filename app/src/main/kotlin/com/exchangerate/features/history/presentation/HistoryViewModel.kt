package com.exchangerate.features.history.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.history.business.HistoryFilter
import com.exchangerate.features.history.business.HistoryInterpreter
import com.exchangerate.features.history.business.HistoryRouter
import com.exchangerate.features.history.data.HistoryAction
import com.exchangerate.features.history.data.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryIntent
import javax.inject.Inject

class HistoryViewModel(
        filter: HistoryFilter,
        interpreter: HistoryInterpreter,
        router: HistoryRouter,
        store: MviStore<HistoryState>
) : MviViewModel<HistoryIntent, HistoryAction, HistoryState>(filter, interpreter, router, store)

@Suppress("UNCHECKED_CAST")
open class HistoryViewModelFactory @Inject constructor(
        private val filter: HistoryFilter,
        private val interpreter: HistoryInterpreter,
        private val router: HistoryRouter,
        private val store: MviStore<HistoryState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HistoryViewModel(filter, interpreter, router, store) as T
    }
}