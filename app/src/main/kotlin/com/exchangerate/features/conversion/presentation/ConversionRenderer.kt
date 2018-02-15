package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.conversion.data.ConversionState

class ConversionRenderer(
        private val screenConverter: ConversionScreenConverter
) : MviRenderer<ConversionState, ConversionView> {

    override fun render(state: ConversionState?, view: ConversionView) {
        state?.apply {
            view.renderLoading(state.isLoading)
            state.currencyData.apply {
                if (!this.isInitialized) {
                    view.renderCurrencyData(this.currencies)
                }
            }
            state.conversionData.apply {
                val screenModel = screenConverter.prepareForPresentation(this)
                view.renderConversionData(screenModel)
            }
            state.error?.apply {
                view.renderError()
            }
        }
    }
}