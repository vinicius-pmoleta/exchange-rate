package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.conversion.data.ConversionState

class ConversionRenderer(
        private val screenConverter: ConversionScreenConverter
) : MviRenderer<ConversionState, ConversionView> {

    override fun render(state: ConversionState?, view: ConversionView) {
        state?.apply {
            view.renderLoading(state.isLoading)
            state.currencyData.let {
                if (it.currencies.isNotEmpty()) {
                    view.renderCurrencyData(
                            it.currencies,
                            state.conversionData.fromCurrency,
                            state.conversionData.toCurrency)
                }
            }
            state.conversionData.let {
                val screenModel = screenConverter.prepareForPresentation(it)
                view.renderConversionData(screenModel)
            }
            state.error?.let {
                view.renderError()
            }
        }
    }
}