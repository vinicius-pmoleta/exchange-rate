package com.exchangerate.features.exchange.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.exchange.data.ExchangeState

interface ExchangeView : MviView<ExchangeIntent, ExchangeState>