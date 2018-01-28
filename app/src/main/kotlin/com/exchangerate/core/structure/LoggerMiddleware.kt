package com.exchangerate.core.structure

import android.util.Log
import com.exchangerate.BuildConfig

class LoggerMiddleware : MviMiddleware {

    companion object {
        val TAG: String = LoggerMiddleware::class.java.simpleName
    }

    override fun intercept(oldState: MviState, action: MviAction, newState: MviState) {
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.d(TAG, "---------------------------")
        Log.d(TAG, "@@@@ LOGGER MIDDLEWARE @@@@")
        Log.d(TAG, "---------------------------")
        Log.d(TAG, "[[ OLD STATE ]] " + oldState)
        Log.d(TAG, "[[ ACTION    ]] " + action)
        Log.d(TAG, "[[ NEW STATE ]] " + newState)
        Log.d(TAG, "---------------------------")
    }
}