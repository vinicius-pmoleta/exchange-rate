package com.exchangerate.features.history.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.history.data.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryIntent

interface HistoryView : MviView<HistoryIntent, HistoryState>