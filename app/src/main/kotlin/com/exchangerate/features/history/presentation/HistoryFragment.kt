package com.exchangerate.features.history.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.LivePagedListProvider
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.databinding.HistoryFragmentBinding
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.di.DaggerHistoryFeatureComponent
import com.exchangerate.features.history.di.HistoryFeatureModule
import com.exchangerate.features.history.presentation.model.HistoryInitialIntent
import com.exchangerate.features.history.presentation.model.HistoryIntent
import com.exchangerate.features.history.presentation.model.HistoryScreenModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.history_fragment.historyView
import javax.inject.Inject

class HistoryFragment : BaseFragment(), HistoryView {

    @Inject
    lateinit var viewModelFactory: HistoryViewModelFactory

    @Inject
    lateinit var renderer: HistoryRenderer

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var viewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    override fun initializeDependencyInjector() {
        DaggerHistoryFeatureComponent.builder()
                .applicationComponent((activity?.application as ExchangeRateApplication).applicationComponent)
                .historyFeatureModule(HistoryFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HistoryViewModel::class.java)
        configureHistoryList()
        setup()
    }

    private fun setup() {
        viewModel.liveStates().observe(this, Observer { state -> render(state) })
        viewModel.processIntents(intents())
    }

    private fun configureHistoryList() {
        historyView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        historyView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        adapter = HistoryAdapter()
        historyView.adapter = adapter
    }

    override fun onDestroy() {
        LiveDataOperator.removeResultObservers(viewModel.liveStates(), this)
        super.onDestroy()
    }

    override fun intents(): Observable<HistoryIntent> = Observable.just(HistoryInitialIntent())

    override fun render(state: HistoryState?) {
        renderer.render(state, this)
    }

    override fun renderData(screenModel: HistoryScreenModel, pagedListConfiguration: PagedList.Config) {
        binding.history = screenModel
        screenModel.history?.let {
            val historyLiveData = LivePagedListBuilder(screenModel.history, pagedListConfiguration).build()
            historyLiveData.observe(this, Observer { pagedList -> adapter.submitList(pagedList) })
        }
    }

    override fun renderError() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }
}