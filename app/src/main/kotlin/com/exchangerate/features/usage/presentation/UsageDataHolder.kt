package com.exchangerate.features.usage.presentation

import com.exchangerate.core.data.live.LiveResult
import com.exchangerate.core.structure.BaseDataHolder
import com.exchangerate.features.usage.data.Usage

class UsageDataHolder(val result: LiveResult<Usage>? = null) : BaseDataHolder()