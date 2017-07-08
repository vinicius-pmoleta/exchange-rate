package com.exchangerate.features.usage.presentation

import android.os.Bundle
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.structure.BaseActivity
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import com.exchangerate.features.usage.di.UsageUseCasesModule
import kotlinx.android.synthetic.main.usage_activity.*

class UsageActivity : BaseActivity<UsageContract.Action>(), UsageContract.View {

    override fun initializeDependencyInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((application as ExchangeRateApplication).applicationComponent)
                .usageFeatureModule(UsageFeatureModule(this))
                .usageUseCasesModule(UsageUseCasesModule())
                .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usage_activity)

        presenter.loadCurrentUsage()
    }

    override fun displayCurrentUsage(usage: UsageViewModel) {
        usageStatusView.text = usage.toString()
    }

}