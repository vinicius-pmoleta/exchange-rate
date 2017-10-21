package com.exchangerate.core.data.usecase

import com.exchangerate.core.data.live.LiveDataReactiveConverter
import com.exchangerate.core.data.live.LiveResult
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Subscription

abstract class LiveUseCase<T : Any, in Params>(private val configuration: ExecutionConfiguration) {

    private val defaultOnSubscribe: (Subscription) -> Unit = {}
    private val defaultTransformer = FlowableTransformer<T, T> {
        it
                .subscribeOn(configuration.execution.scheduler)
                .observeOn(configuration.postExecution.scheduler)
    }

    abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    fun execute(params: Params? = null,
                onSubscribe: (Subscription) -> Unit = defaultOnSubscribe,
                transformer: FlowableTransformer<T, T> = defaultTransformer): Flowable<T> {

        return buildUseCaseObservable(params)
                .compose(transformer)
                .doOnSubscribe(onSubscribe)
    }

    fun executeLive(params: Params? = null,
                    onSubscribe: (Subscription) -> Unit = defaultOnSubscribe,
                    transformer: FlowableTransformer<T, T> = defaultTransformer): LiveResult<T> {

        val result = execute(params, onSubscribe, transformer)
        return LiveDataReactiveConverter.fromPublisher(result)
    }
}