package com.exchangerate.features.usage.data

import com.google.gson.annotations.SerializedName

data class UsageResponse(val status: Int, val data: Data)

data class Data(val status: String, val usage: Usage)

data class Usage(
        @SerializedName("requests") val sent: Int,
        @SerializedName("requests_quota") val quota: Int,
        @SerializedName("requests_remaining") val remaining: Int,
        @SerializedName("daily_average") val averagePerDay: Int
)
