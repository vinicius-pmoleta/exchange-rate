package com.exchangerate.core.structure

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BasePresenterContract {

    fun releaseResources()

}

abstract class BasePresenter {

    private val disposables = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        checkNotNull(disposable)
        disposables.add(disposable)
    }

    fun disposeAll() {
        disposables.dispose()
    }

}
