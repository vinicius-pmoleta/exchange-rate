package com.exchangerate.core.data.live

import android.arch.lifecycle.LifecycleOwner

class LiveDataOperator {

    companion object {
        fun <T> isDataAvailable(result: LiveResult<T>?): Boolean {
            return result?.data?.value != null
        }

        fun <T> removeResultObservers(result: LiveResult<T>?, owner: LifecycleOwner) {
            result?.data?.removeObservers(owner)
            result?.error?.removeObservers(owner)
        }
    }

}