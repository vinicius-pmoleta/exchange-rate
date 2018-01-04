package com.exchangerate.features.usage.mvi

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.structure.MviView
import com.exchangerate.features.usage.di.DaggerUsageFeatureComponent
import com.exchangerate.features.usage.di.UsageFeatureModule
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UsageFragment : Fragment(), MviView<UsageIntent, UsageViewState> {

    companion object {
        val TAG: String = UsageFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: UsagePresenter

    @Inject
    lateinit var screenConverter: UsageScreenConverter

    private lateinit var viewModel: UsageViewModel
    private val disposables by lazy { CompositeDisposable() }

    private fun initializeDependencyInjector() {
        DaggerUsageFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .usageFeatureModule(UsageFeatureModule())
                .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDependencyInjector()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.usage_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UsageViewModel::class.java)
        bind()
    }

    private fun bind() {
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.bindPresenter(presenter)
        viewModel.processIntents(intents())
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<UsageIntent> {
        return Observable.just(InitialIntent())
    }

    override fun render(state: UsageViewState) {
    }

}