package com.exchangerate.features.usage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import javax.inject.Inject

class UsageActivity : AppCompatActivity(), UsageContract.View {

    @Inject
    lateinit var presenter: UsageContract.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usage_activity)
        initializeInjector()
    }

    private fun initializeInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((application as ExchangeRateApplication).applicationComponent)
                .build().inject(this)
    }

}