package com.exchangerate.core.data.repository.remote

import com.exchangerate.core.data.repository.remote.data.RatesResponse
import com.exchangerate.core.data.repository.remote.data.UsageResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RemoteExchangeRepository {

    @GET("usage.json")
    fun getUsage(): Single<UsageResponse>

    @GET("latest.json")
    fun getLatestRates(): Single<RatesResponse>

}