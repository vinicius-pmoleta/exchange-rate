package com.exchangerate.features.usage.presentation.model

import com.exchangerate.core.structure.MviIntent

interface UsageIntent : MviIntent

class LoadUsageIntent : UsageIntent