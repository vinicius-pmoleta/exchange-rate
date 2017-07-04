package com.exchangerate.features.usage.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import com.exchangerate.features.usage.di.UsageUseCasesModule
import kotlinx.android.synthetic.main.usage_activity.*
import javax.inject.Inject

class UsageActivity : AppCompatActivity(), UsageContract.View {

    @Inject
    lateinit var presenter: UsageContract.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usage_activity)
        initializeInjector()
        presenter.loadCurrentUsage()
    }

    override fun onStop() {
        presenter.releaseResources()
        super.onStop()
    }

    override fun displayCurrentUsage(usage: UsageViewModel) {
        usageStatusView.text = usage.toString()
    }

    private fun initializeInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((application as ExchangeRateApplication).applicationComponent)
                .usageFeatureModule(UsageFeatureModule(this))
                .usageUseCasesModule(UsageUseCasesModule())
                .build().inject(this)
    }

}