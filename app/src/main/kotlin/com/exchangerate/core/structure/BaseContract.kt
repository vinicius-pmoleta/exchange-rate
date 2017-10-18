package com.exchangerate.core.structure

import android.arch.lifecycle.LifecycleOwner

interface BaseContract {

    interface View {

        fun provideLifecycleOwner(): LifecycleOwner

    }

    interface Action

}