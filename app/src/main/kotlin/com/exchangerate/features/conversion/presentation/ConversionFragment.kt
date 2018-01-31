package com.exchangerate.features.conversion.presentation

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
import com.exchangerate.features.conversion.data.ConversionState
import com.exchangerate.features.conversion.di.ConversionFeatureModule
import com.exchangerate.features.conversion.di.DaggerConversionFeatureComponent
import com.exchangerate.features.conversion.presentation.model.ApplyConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionIntent
import com.exchangerate.features.conversion.presentation.model.ConversionScreenModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.conversion_fragment.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ConversionFragment : BaseFragment(), ConversionView {

    companion object {
        val TAG: String = ConversionFragment::class.java.simpleName
    }

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

    override fun intents(): Observable<ConversionIntent> {
        view?.run {
            return RxTextView
                    .textChanges(this.conversionValueToConvertView)
                    .filter { value -> value.isNotEmpty() }
                    .switchMap { value ->
                        RxTextView
                                .textChanges(this.conversionCurrencyFromView)
                                .filter { fromCurrency -> fromCurrency.length == 3 }
                                .switchMap { fromCurrency ->
                                    RxTextView
                                            .textChanges(this.conversionCurrencyToView)
                                            .filter { toCurrency -> toCurrency.length == 3 }
                                            .debounce(500, TimeUnit.MILLISECONDS)
                                            .map { toCurrency ->
                                                ApplyConversionIntent(fromCurrency.toString(), toCurrency.toString(), value.toString().toFloat())
                                            }
                                }
                    }
        }
        return Observable.empty()
    }

    override fun render(state: ConversionState?) {
        renderer.render(state, this)
    }

    override fun renderLoading(isLoading: Boolean) {
    }

    override fun renderData(conversion: ConversionScreenModel) {
        view?.conversionConvertedValueView?.text = conversion.convertedValue
        view?.conversionRateView?.text = conversion.rate
    }

    override fun renderError() {
        Toast.makeText(context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }
}