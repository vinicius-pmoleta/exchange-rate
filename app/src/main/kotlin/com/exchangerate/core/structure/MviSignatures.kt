package com.exchangerate.core.structure

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

interface MviAction

interface MviIntent

interface MviProcessor

interface MviState

interface MviView<I : MviIntent, in S : MviState> {

    fun intents(): Observable<I>

    fun render(state: S)
}

interface MviRenderer<in S : MviState> {

    fun render(state: S)
}

open class MviViewModel<I : MviIntent, S : MviState>(
        private val interpreter: MviIntentInterpreter<I>,
        private val store: MviStore<S>
) : ViewModel() {

    private lateinit var disposable: Disposable

    fun processIntents(intents: Observable<I>) {
        disposable = intents
                .flatMap { intent -> Observable.fromIterable(interpreter.translate(intent)) }
                .flatMap { action -> store.dispatch(action) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun states(): Observable<S> = store.stateObserver

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

interface MviIntentInterpreter<in I : MviIntent> {

    fun translate(intent: I): List<MviAction>
}

interface MviReducer<S : MviState> {

    fun reduce(action: MviAction, state: S): Observable<S>

    fun initialState(): S
}

class MviStore<S : MviState>(private val reducer: MviReducer<S>) {

    private var state = reducer.initialState()

    val stateObserver: BehaviorSubject<S> = BehaviorSubject
            .createDefault(reducer.initialState())

    fun dispatch(action: MviAction): Observable<S> {
        return reducer.reduce(action, state)
                .doOnNext { update ->
                    state = update
                    stateObserver.onNext(state)
                }
    }
}
