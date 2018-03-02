package com.exchangerate.features.history.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.databinding.HistoryFragmentBinding
import com.exchangerate.features.history.data.HistoryState
import com.exchangerate.features.history.di.DaggerHistoryFeatureComponent
import com.exchangerate.features.history.di.HistoryFeatureModule
import com.exchangerate.features.history.presentation.model.HistoryInitialIntent
import com.exchangerate.features.history.presentation.model.HistoryIntent
import io.reactivex.Observable
import javax.inject.Inject

class HistoryFragment : BaseFragment(), HistoryView {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    @Inject
    lateinit var renderer: HistoryRenderer

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var viewModel: HistoryViewModel

    override fun initializeDependencyInjector() {
        DaggerHistoryFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .historyFeatureModule(HistoryFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel::class.java)
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

    override fun intents(): Observable<HistoryIntent> = Observable.just(HistoryInitialIntent())

    override fun render(state: HistoryState?) {
        renderer.render(state, this)
    }
}