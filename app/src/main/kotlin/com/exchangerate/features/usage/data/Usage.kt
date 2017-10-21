package com.exchangerate.features.usage.data

import com.squareup.moshi.Json

data class UsageResponse(val status: Int, val data: Data)

data class Data(val status: String, val usage: Usage)

data class Usage(
        @Json(name = "requests") val sent: Int,
        @Json(name = "requests_quota") val quota: Int,
        @Json(name = "requests_remaining") val remaining: Int,
        @Json(name = "daily_average") val averagePerDay: Int
)
