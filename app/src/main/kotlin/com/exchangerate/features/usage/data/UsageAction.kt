package com.exchangerate.features.usage.data

import com.exchangerate.core.structure.MviAction

interface UsageAction : MviAction

class StartLoadingUsageAction : UsageAction
class LoadUsageAction : UsageAction