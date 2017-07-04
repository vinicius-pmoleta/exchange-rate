package com.exchangerate.core.data.usecase

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber

abstract class UseCase<T, Params>(val configuration: ExecutionConfiguration) {

    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    fun execute(subscriber: DisposableSubscriber<T>, params: Params): Disposable {
        checkNotNull(subscriber)

        val transformer = FlowableTransformer<T, T> {
            it
                    .subscribeOn(configuration.execution.scheduler)
                    .observeOn(configuration.postExecution.scheduler)
        }
        return execute(subscriber, params, transformer)
    }

    fun execute(subscriber: DisposableSubscriber<T>, params: Params, transformer: FlowableTransformer<T, T>): Disposable {
        return buildUseCaseObservable(params)
                .compose(transformer)
                .subscribeWith(subscriber)
    }

}