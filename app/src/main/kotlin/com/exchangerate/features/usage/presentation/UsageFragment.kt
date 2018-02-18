package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import com.exchangerate.features.usage.presentation.model.LoadUsageIntent
import com.exchangerate.features.usage.presentation.model.UsageInitialIntent
import com.exchangerate.features.usage.presentation.model.UsageIntent
import com.exchangerate.features.usage.presentation.model.UsageScreenModel
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import io.reactivex.Observable
import kotlinx.android.synthetic.main.usage_fragment.view.usageAverageView
import kotlinx.android.synthetic.main.usage_fragment.view.usagePercentageView
import kotlinx.android.synthetic.main.usage_fragment.view.usagePullToRefreshView
import kotlinx.android.synthetic.main.usage_fragment.view.usageRemainingRequestsView
import javax.inject.Inject

class UsageFragment : BaseFragment(), UsageView {

    @Inject
    lateinit var viewModelFactory: UsageViewModelFactory

    @Inject
    lateinit var renderer: UsageRenderer

    private lateinit var viewModel: UsageViewModel

    override fun initializeDependencyInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .usageFeatureModule(UsageFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.usage_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UsageViewModel::class.java)
        setup()
    }

    private fun setup() {
        viewModel.liveStates().observe(this, Observer { state -> render(state) })
        viewModel.processIntents(intents())
    }

    override fun onDestroy() {
        LiveDataOperator.removeResultObservers(viewModel.liveStates(), this)
        super.onDestroy()
    }

    override fun intents(): Observable<UsageIntent> = Observable.merge(initialIntent(), refreshIntent())

    override fun render(state: UsageState?) {
        renderer.render(state, this)
    }

    override fun renderLoading(isLoading: Boolean) {
        view?.usagePullToRefreshView?.isRefreshing = isLoading
    }

    override fun renderData(usage: UsageScreenModel) {
        view?.usagePercentageView?.text = getString(R.string.usage_information_percentage_used, usage.usedPercentage)
        view?.usageAverageView?.text = getString(R.string.usage_information_daily_average_requests, usage.averagePerDay)
        view?.usageRemainingRequestsView?.text = getString(R.string.usage_information_remaining_requests, usage.remainingRequests)
    }

    override fun renderError() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }

    private fun initialIntent(): Observable<UsageIntent> = Observable.just(UsageInitialIntent())

    private fun refreshIntent(): Observable<UsageIntent> {
        view?.run {
            return RxSwipeRefreshLayout
                    .refreshes(view?.usagePullToRefreshView!!)
                    .map { _ -> LoadUsageIntent() }
        }
        return Observable.empty()
    }
}