package com.exchangerate.features.home.presentation

import android.os.Bundle
import com.exchangerate.R
import com.exchangerate.core.structure.BaseActivity
import com.exchangerate.features.usage.presentation.UsageFragment

class HomeActivity : BaseActivity<HomePresenter>(), HomeContract.View {

    override fun initializeDependencyInjector() {
        presenter = HomePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        presenter.initialize()
    }

    override fun showUsage() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.homeContent, UsageFragment())
                .commit()
    }

}