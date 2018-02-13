package com.exchangerate.core.structure

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.exchangerate.core.data.live.LiveDataReactiveConverter
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

    fun render(state: S?)
}

interface MviRenderer<in S : MviState, in V : MviView<*, *>> {

    fun render(state: S?, view: V)
}

open class MviViewModel<I : MviIntent, S : MviState>(
        private val interpreter: MviIntentInterpreter<I>,
        private val store: MviStore<S>
) : ViewModel() {

    private val liveState: LiveData<S> by lazy {
        LiveDataReactiveConverter.fromPublisher(store.stateObserver)
    }

    private lateinit var disposable: Disposable

    fun processIntents(intents: Observable<I>) {
        disposable = intents
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap { intent -> Observable.fromIterable(interpreter.translate(intent)) }
                .flatMap { action -> store.dispatch(action) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun liveStates(): LiveData<S> = liveState

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

interface MviMiddleware {

    fun intercept(oldState: MviState, action: MviAction, newState: MviState)
}

class MviStore<S : MviState>(private val reducer: MviReducer<S>) {

    private var state = reducer.initialState()
    private val middleware: List<MviMiddleware> = listOf(LoggerMiddleware())

    val stateObserver: BehaviorSubject<S> = BehaviorSubject
            .createDefault(reducer.initialState())

    fun dispatch(action: MviAction): Observable<S> {
        return reducer.reduce(action, state)
                .doOnNext { newState ->
                    middleware.forEach { it.intercept(state, action, newState) }
                    state = newState
                    stateObserver.onNext(state)
                }
    }
}
