package com.exchangerate.features.usage.mvi

import android.arch.lifecycle.ViewModel
import com.exchangerate.core.structure.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class UsageViewModel : ViewModel(), MviViewModel<UsageIntent, UsageViewState> {

    private val intentSubjects: PublishSubject<UsageIntent> = PublishSubject.create()
    private val statesObservable: Observable<UsageViewState> = compose()

    private lateinit var presenter: UsagePresenter

    fun bindPresenter(presenter: UsagePresenter) {
        this.presenter = presenter
    }

    override fun processIntents(intents: Observable<UsageIntent>) {
        intents.subscribe(intentSubjects)
    }

    override fun states(): Observable<UsageViewState> {
        return statesObservable
    }

    private fun compose(): Observable<UsageViewState> {
        return intentSubjects
                .compose(presenter.intentFilter())
                .map { intent -> presenter.actionFromIntent(intent) }
                .compose(presenter.actionProcessor())
                .scan(UsageViewState(), presenter.reducer())
                .replay(1)
                .autoConnect(0)
    }

}