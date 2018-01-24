package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviIntent

interface UsageIntent : MviIntent

class LoadUsageIntent : UsageIntent