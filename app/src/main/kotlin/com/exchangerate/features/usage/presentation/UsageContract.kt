package com.exchangerate.features.usage.presentation

import com.exchangerate.core.structure.BasePresenterContract
import com.exchangerate.features.usage.data.UsageViewModel

interface UsageContract {

    interface View {

        fun displayCurrentUsage(usage: UsageViewModel)

    }

    interface Action : BasePresenterContract {

        fun loadCurrentUsage()

    }

}