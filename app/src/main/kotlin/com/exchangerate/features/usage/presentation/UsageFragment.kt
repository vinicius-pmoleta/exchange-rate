package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.features.usage.data.UsageViewModel
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import com.exchangerate.features.usage.di.UsageUseCasesModule
import kotlinx.android.synthetic.main.usage_fragment.*


class UsageFragment : BaseFragment<UsageContract.Action>(), UsageContract.View {

    companion object {
        val TAG: String = UsageFragment::class.java.simpleName
    }

    override fun initializeDependencyInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .usageFeatureModule(UsageFeatureModule(this))
                .usageUseCasesModule(UsageUseCasesModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.usage_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val liveUsageViewModel = ViewModelProviders.of(this).get(LiveUsageViewModel::class.java)
        presenter.loadCurrentUsage(liveUsageViewModel)
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }

    override fun displayCurrentUsage(usage: UsageViewModel) {
        usageStatusView.text = usage.toString()
    }

    override fun displayErrorUsageNotFetched() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }

}