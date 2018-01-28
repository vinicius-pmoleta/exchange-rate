package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.core.structure.MviView
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UsageFragment : BaseFragment(), MviView<UsageIntent, UsageState> {

    companion object {
        val TAG: String = UsageFragment::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: UsageViewModelFactory

    @Inject
    lateinit var renderer: UsageRenderer

    private lateinit var viewModel: UsageViewModel

    private val disposables by lazy { CompositeDisposable() }

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
        bind()
    }

    private fun bind() {
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<UsageIntent> {
        return Observable.just(LoadUsageIntent())
    }

    override fun render(state: UsageState) {
        renderer.render(state)
    }
}