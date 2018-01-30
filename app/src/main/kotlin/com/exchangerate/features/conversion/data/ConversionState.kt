package com.exchangerate.features.conversion.data

import com.exchangerate.core.structure.MviState

data class ConversionState(
        val isLoading: Boolean = false
) : MviState