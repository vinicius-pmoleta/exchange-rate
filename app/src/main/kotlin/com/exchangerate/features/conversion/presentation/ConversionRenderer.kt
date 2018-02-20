package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel

class ConversionRenderer : MviRenderer<ConversionState, ConversionView> {

    override fun render(state: ConversionState?, view: ConversionView) {
        state?.apply {
            val currencies = prepareCurrenciesToPresent(state.currencyData.currencies)
            val screenModel = ConversionScreenModel(
                    state.isLoading,
                    prepareRateToPresent(state.conversionData.rate),
                    prepareConvertedValueToPresent(state.conversionData.convertedValue),
                    currencies,
                    currencies.indexOf(state.conversionData.fromCurrency),
                    currencies.indexOf(state.conversionData.toCurrency)
            )
            view.renderData(screenModel)

            state.error?.let {
                view.renderError()
            }
        }
    }

    private fun prepareRateToPresent(rate: Float): String {
        return "%.3f".format(rate)
    }

    private fun prepareConvertedValueToPresent(convertedValue: Float): String {
        return "%.2f".format(convertedValue)
    }

    private fun prepareCurrenciesToPresent(data: List<Currency>): List<String> {
        return data.map { item -> item.code }.toList()
    }
}