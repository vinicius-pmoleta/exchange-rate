package com.exchangerate.features.usage.mvi

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsageViewModel(
        private val interpreter: UsageInterpreter,
        private val store: MviStore<UsageState>
) : MviViewModel<UsageIntent, UsageState>, ViewModel() {

    override fun processIntents(intents: Observable<UsageIntent>) {
        intents
                .flatMap { intent ->
                    Observable.fromIterable(interpreter.translate(intent))
                }
                .map { action -> store.dispatch(action) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun states(): Observable<UsageState> = store.stateObserver
}

class UsageViewModelFactory @Inject constructor(
        private val interpreter: UsageInterpreter,
        private val store: MviStore<UsageState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsageViewModel(interpreter, store) as T
    }
}