package com.exchangerate.features.exchange.data

import com.exchangerate.core.structure.MviState

data class ExchangeState(
        val isLoading: Boolean = false
) : MviState