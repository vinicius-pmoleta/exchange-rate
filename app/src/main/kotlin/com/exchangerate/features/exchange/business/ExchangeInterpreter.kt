package com.exchangerate.features.exchange.business

import android.util.Log
import com.exchangerate.core.structure.MviAction
import com.exchangerate.core.structure.MviIntentInterpreter
import com.exchangerate.features.exchange.presentation.ExchangeIntent

class ExchangeInterpreter : MviIntentInterpreter<ExchangeIntent> {

    override fun translate(intent: ExchangeIntent): List<MviAction> {
        Log.d("TEST", "Intent is " + intent)
        return emptyList()
    }
}