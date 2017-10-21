package com.exchangerate.core.data.live

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

class LiveResult<T>(val data: LiveData<T>?, val error: LiveData<Throwable>?) {

    fun observe(owner: LifecycleOwner, dataObserver: Observer<T>?, errorObserver: Observer<Throwable>?) {
        data?.observe(owner, dataObserver)
        error?.observe(owner, errorObserver)
    }

}