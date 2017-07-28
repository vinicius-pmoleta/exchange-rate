package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.BaseContract
import com.exchangerate.features.usage.data.UsageViewModel

interface UsageContract {

    interface View : BaseContract.View {

        fun displayCurrentUsage(usage: UsageViewModel)

        fun displayErrorUsageNotFetched()

    }

    interface Action : BaseContract.Action {

        fun loadCurrentUsage(liveUsageViewModel: LiveUsageViewModel)

    }

}