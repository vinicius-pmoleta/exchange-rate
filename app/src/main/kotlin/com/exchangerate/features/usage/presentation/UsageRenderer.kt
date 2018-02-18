package com.exchangerate.features.usage.presentation

import android.util.Log
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.usage.data.UsageState

class UsageRenderer(private val screenConverter: UsageScreenConverter) : MviRenderer<UsageState, UsageView> {

    override fun render(state: UsageState?, view: UsageView) {
        state?.apply {
            view.renderLoading(state.isLoading)
            state.data?.let {
                val screenModel = screenConverter.prepareForPresentation(it)
                view.renderData(screenModel)
            }
            state.error?.let {
                Log.e("UsageRenderer", "Error on usage state renderer", it)
                view.renderError()
            }
        }
    }


}