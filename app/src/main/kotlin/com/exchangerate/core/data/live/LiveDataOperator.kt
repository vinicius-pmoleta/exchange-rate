package com.exchangerate.core.data.live

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

class LiveDataOperator {

    companion object {
        fun <T> isDataAvailable(data: LiveData<T>?): Boolean {
            return data?.value != null
        }

        fun <T> removeResultObservers(data: LiveData<T>?, owner: LifecycleOwner) {
            data?.removeObservers(owner)
        }
    }

}