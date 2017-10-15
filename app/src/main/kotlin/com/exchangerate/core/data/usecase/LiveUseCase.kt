package com.exchangerate.core.data.usecase

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Subscription

abstract class LiveUseCase<T : Any, in Params>(val configuration: ExecutionConfiguration) {

    private val onSubscribeStub: (Subscription) -> Unit = {}
    private val onErrorStub: (Throwable) -> Unit = { throw RuntimeException("Not implemented", it) }
    private val onCompleteStub: () -> Unit = {}

    abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    fun execute(onSubscribe: (Subscription) -> Unit = onSubscribeStub,
                onError: (Throwable) -> Unit = onErrorStub,
                onComplete: () -> Unit = onCompleteStub,
                params: Params? = null): LiveData<T> {

        val transformer = FlowableTransformer<T, T> {
            it
                    .subscribeOn(configuration.execution.scheduler)
                    .observeOn(configuration.postExecution.scheduler)
        }
        return execute(onSubscribe, onError, onComplete, params, transformer)
    }

    fun execute(onSubscribe: (Subscription) -> Unit = onSubscribeStub,
                onError: (Throwable) -> Unit = onErrorStub,
                onComplete: () -> Unit = onCompleteStub,
                params: Params? = null,
                transformer: FlowableTransformer<T, T>): LiveData<T> {

        val data = buildUseCaseObservable(params)
                .compose(transformer)
                .doOnSubscribe(onSubscribe)
                .doOnError(onError)
                .doOnComplete(onComplete)

        return LiveDataReactiveStreams.fromPublisher(data)
    }
}