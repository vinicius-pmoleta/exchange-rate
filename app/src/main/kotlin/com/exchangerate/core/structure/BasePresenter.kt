package com.exchangerate.core.structure

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter : BaseContract.Action {

    private val disposables = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        checkNotNull(disposable)
        disposables.add(disposable)
    }

    fun disposeAll() {
        disposables.dispose()
    }

    override fun releaseResources() {
        disposeAll()
    }

}
