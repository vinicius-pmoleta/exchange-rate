package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.BaseContract
import com.exchangerate.features.usage.presentation.model.UsageScreenModel

interface UsageContract {

    interface View : BaseContract.View {

        fun displayCurrentUsage(usage: UsageScreenModel)

        fun displayErrorUsageNotFetched()

        fun provideUsageDataHolder(): UsageDataHolder

    }

    interface Action : BaseContract.Action {

        fun loadCurrentUsage()

    }

}