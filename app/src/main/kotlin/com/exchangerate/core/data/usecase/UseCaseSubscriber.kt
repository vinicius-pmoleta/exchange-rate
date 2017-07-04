package com.exchangerate.core.data.usecase

import io.reactivex.subscribers.DisposableSubscriber

open class UseCaseSubscriber<T> : DisposableSubscriber<T>() {
    override fun onNext(next: T) {
    }

    override fun onError(error: Throwable?) {
    }

    override fun onComplete() {
    }
}