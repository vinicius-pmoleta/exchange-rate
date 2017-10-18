package com.exchangerate.features.home.presentation

class HomePresenter(val view: HomeContract.View) : HomeContract.Action {

    override fun initialize() {
        view.showUsage()
    }

}