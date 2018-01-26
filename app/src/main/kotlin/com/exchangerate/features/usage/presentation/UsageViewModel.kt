package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.exchangerate.core.structure.MviStore
import com.exchangerate.core.structure.MviViewModel
import com.exchangerate.features.usage.business.UsageInterpreter
import com.exchangerate.features.usage.data.UsageState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsageViewModel(
        private val interpreter: UsageInterpreter,
        private val store: MviStore<UsageState>
) : MviViewModel<UsageIntent, UsageState>, ViewModel() {

    private lateinit var disposable: Disposable

    override fun processIntents(intents: Observable<UsageIntent>) {
        disposable = intents
                .flatMap { intent -> Observable.fromIterable(interpreter.translate(intent)) }
                .flatMap { action -> store.dispatch(action) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun states(): Observable<UsageState> = store.stateObserver

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

class UsageViewModelFactory @Inject constructor(
        private val interpreter: UsageInterpreter,
        private val store: MviStore<UsageState>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsageViewModel(interpreter, store) as T
    }
}