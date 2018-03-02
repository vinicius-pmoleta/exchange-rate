package com.exchangerate.features.conversion.business

import com.exchangerate.core.structure.MviFilter
import com.exchangerate.core.structure.MviStore
import com.exchangerate.features.conversion.data.model.ConversionState
import com.exchangerate.features.conversion.data.model.SuccessfulCurrenciesResultAction
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent

class ConversionFilter(
        private val store: MviStore<ConversionState>
) : MviFilter<ConversionIntent, ConversionState> {

    override fun apply(intent: ConversionIntent, state: ConversionState?): Boolean {
        var shouldFilter = true
        when (intent) {
            is LoadCurrenciesIntent -> state?.currencyData?.currencies?.let {
                if (it.isNotEmpty()) {
                    store.dispatch(SuccessfulCurrenciesResultAction(it))
                    shouldFilter = false
                }
            }
        }
        return shouldFilter
    }
}