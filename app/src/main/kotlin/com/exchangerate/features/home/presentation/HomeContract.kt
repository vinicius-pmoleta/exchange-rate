package com.exchangerate.features.home.presentation

import com.exchangerate.core.structure.BaseContract

interface HomeContract {

    interface View {

        fun showUsage()
        fun showConversion()
        fun showAlerts()

    }

    interface Action : BaseContract.Action {

        fun initialize()

    }

}