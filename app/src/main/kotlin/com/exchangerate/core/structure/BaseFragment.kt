package com.exchangerate.core.structure

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import javax.inject.Inject

abstract class BaseFragment<Presenter : BaseContract.Action> : LifecycleFragment() {

    @Inject
    lateinit var presenter: Presenter

    abstract fun initializeDependencyInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDependencyInjector()
    }

    override fun onStop() {
        presenter.releaseResources()
        super.onStop()
    }

}