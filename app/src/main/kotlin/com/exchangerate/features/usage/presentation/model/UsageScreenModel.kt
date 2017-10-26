package com.exchangerate.features.usage.presentation.model

data class UsageScreenModel(val averagePerDay: Int,
                            val usedPercentage: Float,
                            val remainingRequests: Int)