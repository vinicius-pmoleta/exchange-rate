package com.exchangerate.core.structure

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface MviAction

interface MviIntent

interface MviProcessor

interface MviState

interface MviView<I : MviIntent, in S : MviState> {

    fun intents(): Observable<I>

    fun render(state: S)
}

interface MviViewModel<I : MviIntent, S : MviState> {

    fun processIntents(intents: Observable<I>)

    fun states(): Observable<S>
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
