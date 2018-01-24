package com.exchangerate.features.usage.mvi

import com.exchangerate.core.structure.MviAction

interface UsageAction : MviAction

class StartLoadingUsageAction : UsageAction
class LoadUsageAction : UsageAction