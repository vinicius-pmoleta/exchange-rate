package com.exchangerate.features.history.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryIntent
import com.exchangerate.features.history.presentation.model.HistoryScreenModel

interface HistoryView : MviView<HistoryIntent, HistoryState> {

    fun renderData(screenModel: HistoryScreenModel)

    fun renderError()
}