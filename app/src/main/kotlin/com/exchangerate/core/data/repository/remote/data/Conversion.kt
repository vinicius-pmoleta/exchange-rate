package com.exchangerate.core.data.repository.remote.data

import com.squareup.moshi.Json

data class Metadata(
        val timestamp: Long,
        val rate: Float
)

data class ConversionResponse(
        @Json(name = "meta") val metadata: Metadata,
        @Json(name = "response") val convertedValue: Float
)