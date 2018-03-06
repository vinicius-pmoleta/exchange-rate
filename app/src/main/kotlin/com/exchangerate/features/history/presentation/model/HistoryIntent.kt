package com.exchangerate.features.history.presentation.model

import com.exchangerate.core.structure.MviIntent

sealed class HistoryIntent : MviIntent

class HistoryInitialIntent : HistoryIntent()

