package com.exchangerate.features.history.data

import com.exchangerate.core.structure.MviState

data class HistoryState(
        val isLoading: Boolean = false
) : MviState