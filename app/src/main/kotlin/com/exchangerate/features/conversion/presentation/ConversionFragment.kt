package com.exchangerate.features.conversion.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.databinding.ConversionFragmentBinding
import com.exchangerate.features.conversion.data.model.ConversionState
import com.exchangerate.features.conversion.di.ConversionFeatureModule
import com.exchangerate.features.conversion.di.DaggerConversionFeatureComponent
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.conversion_fragment.view.conversionCurrencyFromView
import kotlinx.android.synthetic.main.conversion_fragment.view.conversionCurrencyToView
import kotlinx.android.synthetic.main.conversion_fragment.view.conversionValueToConvertView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConversionFragment : BaseFragment(), ConversionView {

    companion object {
        private val CONVERSION_DEBOUNCE_DURATION = 500L
    }

    @Inject
    lateinit var viewModelFactory: ConversionViewModelFactory

    @Inject
    lateinit var renderer: ConversionRenderer

    private lateinit var binding: ConversionFragmentBinding
    private lateinit var viewModel: ConversionViewModel

    override fun initializeDependencyInjector() {
        DaggerConversionFeatureComponent.builder()
                .applicationComponent((activity?.application as ExchangeRateApplication).applicationComponent)
                .conversionFeatureModule(ConversionFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.conversion_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConversionViewModel::class.java)
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

    override fun intents(): Observable<ConversionIntent> = Observable.merge(initialIntent(), conversionIntent())

    override fun render(state: ConversionState?) {
        renderer.render(state, this)
    }

    override fun renderData(data: ConversionScreenModel) {
        binding.conversion = data
    }

    override fun renderError() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }

    private fun initialIntent(): Observable<ConversionIntent> = Observable.just(LoadCurrenciesIntent())

    private fun conversionIntent(): Observable<ConversionIntent> {
        view?.run {
            return Observables
                    .combineLatest(
                            RxAdapterView.itemSelections(this.conversionCurrencyFromView),
                            RxAdapterView.itemSelections(this.conversionCurrencyToView),
                            RxTextView.textChanges(this.conversionValueToConvertView))
                    .filter { triple -> triple.third.isNotEmpty() }
                    .distinctUntilChanged()
                    .debounce(CONVERSION_DEBOUNCE_DURATION, TimeUnit.MILLISECONDS)
                    .map { triple ->
                        ApplyConversionIntent(
                                this.conversionCurrencyFromView.adapter.getItem(triple.first) as String,
                                this.conversionCurrencyToView.adapter.getItem(triple.second) as String,
                                triple.third.toString().toFloat())
                    }
        }
        return Observable.empty()
    }
}