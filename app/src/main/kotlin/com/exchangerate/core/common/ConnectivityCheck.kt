package com.exchangerate.core.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityCheck(val context: Context) {

    fun hasNetwork(): Boolean? {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected
    }

}