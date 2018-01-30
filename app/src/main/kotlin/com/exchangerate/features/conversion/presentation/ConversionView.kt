package com.exchangerate.features.conversion.presentation

import com.exchangerate.core.structure.MviView
import com.exchangerate.features.conversion.data.ConversionState

interface ConversionView : MviView<ConversionIntent, ConversionState>