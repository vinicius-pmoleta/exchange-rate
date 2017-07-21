package com.exchangerate.core.data.repository.remote.interceptor

import android.content.Context
import com.exchangerate.core.common.ConnectivityCheck
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        if (ConnectivityCheck(context).hasNetwork() == false) {
            val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

            request = request?.newBuilder()
                    ?.cacheControl(cacheControl)
                    ?.build()
        }
        return chain?.proceed(request)
    }

}