package com.exchangerate.core.data.usecase

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.OnErrorNotImplementedException

abstract class UseCase<T: Any, in Params>(val configuration: ExecutionConfiguration) {

    private val onNextStub: (Any) -> Unit = {}
    private val onErrorStub: (Throwable) -> Unit = { throw OnErrorNotImplementedException(it) }
    private val onCompleteStub: () -> Unit = {}

    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    fun execute(onNext: (T) -> Unit = onNextStub,
                onError: (Throwable) -> Unit = onErrorStub,
                onComplete: () -> Unit = onCompleteStub,
                params: Params): Disposable {

        val transformer = FlowableTransformer<T, T> {
            it
                    .subscribeOn(configuration.execution.scheduler)
                    .observeOn(configuration.postExecution.scheduler)
        }

        return execute(onNext, onError, onComplete, params, transformer)
    }

    fun execute(onNext: (T) -> Unit = onNextStub,
                onError: (Throwable) -> Unit = onErrorStub,
                onComplete: () -> Unit = onCompleteStub,
                params: Params, transformer: FlowableTransformer<T, T>): Disposable {

        return buildUseCaseObservable(params)
                .compose(transformer)
                .subscribe(onNext, onError, onComplete)
    }
}