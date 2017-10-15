package com.exchangerate.core.structure

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import javax.inject.Inject

abstract class BaseActivity<Presenter : BaseContract.Action> : LifecycleActivity() {

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