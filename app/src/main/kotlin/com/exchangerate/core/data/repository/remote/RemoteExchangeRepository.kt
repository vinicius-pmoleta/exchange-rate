package com.exchangerate.core.data.repository.remote

import com.exchangerate.core.data.repository.remote.data.ConversionResponse
import com.exchangerate.core.data.repository.remote.data.UsageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteExchangeRepository {

    @GET("usage.json")
    fun getUsage(): Single<UsageResponse>

    @GET("convert/{value}/{fromCurrency}/{toCurrency}")
    fun getConversion(
            @Path("value") value: Float,
            @Path("fromCurrency") fromCurrency: String,
            @Path("toCurrency") toCurrency: String
    ): Single<ConversionResponse>

}