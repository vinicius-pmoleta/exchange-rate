package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.conversion.data.ConversionState

class ConversionRenderer(private val screenConverter: ConversionScreenConverter) : MviRenderer<ConversionState, ConversionView> {

    override fun render(state: ConversionState?, view: ConversionView) {
        state?.apply {
            view.renderLoading(state.isLoading)
            state.data.apply {
                val screenModel = screenConverter.prepareForPresentation(this)
                view.renderData(screenModel)
            }
            state.error?.apply {
                view.renderError()
            }
        }
    }
}