package com.exchangerate.features.exchange.data

import com.exchangerate.core.structure.MviAction

interface ExchangeAction : MviAction

class StartLoadingExchangeAction : ExchangeAction
class LoadExchangeAction : ExchangeAction