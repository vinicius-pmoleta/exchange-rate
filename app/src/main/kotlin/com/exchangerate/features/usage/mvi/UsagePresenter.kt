package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviIntent
import com.exchangerate.core.structure.MviStatus.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class UsagePresenter(private val interactor: UsageInteractor) {

    fun intentFilter(): ObservableTransformer<UsageIntent, UsageIntent> {
        return ObservableTransformer { intents ->
            intents.publish({ shared ->
                Observable.merge(
                        shared.ofType(InitialIntent::class.java).take(1),
                        shared.filter { intent -> intent !is InitialIntent }
                )
            })
        }
    }

    fun actionFromIntent(intent: MviIntent): UsageAction {
        if (intent is InitialIntent) {
            return LoadUsageAction()
        }
        throw IllegalArgumentException("Unknown intent " + intent)
    }

    fun actionProcessor(): ObservableTransformer<UsageAction, UsageResult> {
        return ObservableTransformer { actions ->
            actions.publish({ shared ->
                shared.ofType(LoadUsageAction::class.java)
                        .compose(interactor.loadUsageAction())
                        .cast(UsageResult::class.java)
                        .mergeWith(shared
                                .filter { action -> action !is LoadUsageAction }
                                .flatMap { action -> Observable.error<UsageResult> { IllegalArgumentException("Unknown action " + action) } }
                        )
            })
        }
    }

    fun reducer(): BiFunction<UsageViewState, in UsageResult, UsageViewState> {
        return BiFunction { oldState, result ->
            when (result) {
                is LoadUsageResult -> {
                    when (result.status) {
                        SUCCESS -> UsageViewState(isLoading = false, usage = result.data, error = null)
                        FAILURE -> UsageViewState(isLoading = false, usage = null, error = result.error)
                        IN_FLIGHT -> UsageViewState(isLoading = true)
                    }
                }
            }
        }
    }

}