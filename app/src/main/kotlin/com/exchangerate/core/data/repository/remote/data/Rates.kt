package com.exchangerate.core.data.repository.remote.data

data class RatesResponse(
        val timestamp: Long,
        val base: String,
        val rates: Map<String, Float>
)