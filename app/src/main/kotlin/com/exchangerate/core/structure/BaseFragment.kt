package com.exchangerate.core.structure

import android.os.Bundle
import android.support.v4.app.Fragment
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    abstract fun initializeDependencyInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDependencyInjector()
    }

}