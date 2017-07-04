package com.exchangerate.features.usage

interface UsageContract {

    interface View {}

    interface Action {

        fun loadCurrentUsage()

    }

}