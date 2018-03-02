package com.exchangerate.features.history.presentation

import android.util.Log
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryScreenModel

class HistoryRenderer : MviRenderer<HistoryState, HistoryView> {

    override fun render(state: HistoryState?, view: HistoryView) {
        state?.let {
            val screenModel = HistoryScreenModel(state.isLoading, state.data)
            view.renderData(screenModel)

            it.error?.let {
                Log.e("HistoryRenderer", "Error on usage state renderer", it)
                view.renderError()
            }
        }
    }
}