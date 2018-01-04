package com.exchangerate.core.data.repository.remote

import com.exchangerate.features.usage.data.UsageResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET

interface UsageRepository {

    @GET("usage.json")
    fun getUsage(): Single<UsageResponse>

}