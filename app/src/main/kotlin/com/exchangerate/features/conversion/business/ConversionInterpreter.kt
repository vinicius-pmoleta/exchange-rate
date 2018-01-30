package com.exchangerate.features.conversion.business

import android.util.Log
import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.conversion.data.LoadConversionAction
import com.exchangerate.features.conversion.data.StartLoadingConversionAction
import com.exchangerate.features.conversion.presentation.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.ConversionIntent

class ConversionInterpreter : MviIntentInterpreter<ConversionIntent> {

    override fun translate(intent: ConversionIntent): List<MviAction> {
        Log.d("TEST", "Intent is " + intent)
        return when (intent) {
            is ApplyConversionIntent -> listOf(StartLoadingConversionAction(), LoadConversionAction())
            else -> emptyList()
        }
    }
}