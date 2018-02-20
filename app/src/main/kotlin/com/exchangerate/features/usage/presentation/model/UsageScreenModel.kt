package com.exchangerate.features.usage.presentation.model

data class UsageScreenModel(val isLoading: Boolean = false,
                            val averagePerDay: String = "",
                            val usedPercentage: String = "",
                            val remainingRequests: String = "")