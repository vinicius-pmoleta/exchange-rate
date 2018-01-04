package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviAction

sealed class UsageAction : MviAction

class LoadUsageAction : UsageAction()