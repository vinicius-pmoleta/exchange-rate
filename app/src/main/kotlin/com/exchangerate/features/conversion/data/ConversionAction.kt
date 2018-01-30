package com.exchangerate.features.conversion.data

import com.exchangerate.core.structure.MviAction

interface ConversionAction : MviAction

class StartLoadingConversionAction : ConversionAction
class LoadConversionAction : ConversionAction