package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviIntent

sealed class UsageIntent : MviIntent

class InitialIntent : UsageIntent()