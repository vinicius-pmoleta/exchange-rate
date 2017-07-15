package com.exchangerate.features.home.presentation

import com.exchangerate.core.structure.BasePresenter

class HomePresenter(val view: HomeContract.View): BasePresenter(), HomeContract.Action {

    override fun initialize() {
        view.showUsage()
    }

}