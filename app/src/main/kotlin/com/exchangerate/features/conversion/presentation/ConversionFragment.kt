package com.exchangerate.features.conversion.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.data.Currency
import com.exchangerate.features.conversion.di.ConversionFeatureModule
import com.exchangerate.features.conversion.di.DaggerConversionFeatureComponent
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import com.exchangerate.features.conversion.presentation.model.LoadCurrenciesIntent
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.conversion_fragment.*
import kotlinx.android.synthetic.main.conversion_fragment.view.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConversionFragment : BaseFragment(), ConversionView {

    @Inject
    lateinit var viewModelFactory: ConversionViewModelFactory

    @Inject
    lateinit var renderer: ConversionRenderer

    private lateinit var viewModel: ConversionViewModel

    override fun initializeDependencyInjector() {
        DaggerConversionFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .conversionFeatureModule(ConversionFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.conversion_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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

    override fun renderLoading(isLoading: Boolean) {
        conversionProgressView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun renderCurrencyData(currencies: List<Currency>) {
        val codes = ArrayList<String>()
        currencies.forEach { currency -> codes.add(currency.code) }

        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, codes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        view?.conversionCurrencyFromView?.adapter = adapter
        view?.conversionCurrencyToView?.adapter = adapter
    }

    override fun renderConversionData(conversion: ConversionScreenModel) {
        view?.conversionConvertedValueView?.text = conversion.convertedValue
        view?.conversionRateView?.text = conversion.rate
    }

    override fun renderError() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }

    private fun initialIntent(): Observable<ConversionIntent> = Observable.just(LoadCurrenciesIntent())

    private fun conversionIntent(): Observable<ConversionIntent> {
        view?.run {
            return RxTextView
                    .textChanges(this.conversionValueToConvertView)
                    .filter { value -> value.isNotEmpty() }
                    .switchMap { value ->
                        RxAdapterView
                                .itemSelections(this.conversionCurrencyFromView)
                                .map { position -> this.conversionCurrencyFromView.getItemAtPosition(position) }
                                .switchMap { fromCurrency ->
                                    RxAdapterView
                                            .itemSelections(this.conversionCurrencyToView)
                                            .map { position -> this.conversionCurrencyToView.getItemAtPosition(position) }
                                            .debounce(500, TimeUnit.MILLISECONDS)
                                            .map { toCurrency ->
                                                ApplyConversionIntent(fromCurrency.toString(), toCurrency.toString(), value.toString().toFloat())
                                            }
                                }
                    }
        }
        return Observable.empty()
    }
}