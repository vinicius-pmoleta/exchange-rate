package com.exchangerate.core.data.repository.remote.interceptor

import com.exchangerate.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        val url = request?.url()?.newBuilder()
                ?.addQueryParameter("app_id", BuildConfig.EXCHANGE_RATE_API_KEY)?.build()
        request = request?.newBuilder()?.url(url!!)?.build()
        return chain?.proceed(request!!)
    }

}