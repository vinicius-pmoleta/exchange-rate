package com.exchangerate.features.exchange.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.live.LiveDataOperator
import com.exchangerate.core.structure.BaseFragment
import com.exchangerate.features.exchange.data.ExchangeState
import com.exchangerate.features.exchange.di.DaggerExchangeFeatureComponent
import com.exchangerate.features.exchange.di.ExchangeFeatureModule
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.exchange_fragment.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ExchangeFragment : BaseFragment(), ExchangeView {

    companion object {
        val TAG: String = ExchangeFragment::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: ExchangeViewModelFactory

    @Inject
    lateinit var renderer: ExchangeRenderer

    private lateinit var viewModel: ExchangeViewModel

    override fun initializeDependencyInjector() {
        DaggerExchangeFeatureComponent.builder()
                .applicationComponent((activity.application as ExchangeRateApplication).applicationComponent)
                .exchangeFeatureModule(ExchangeFeatureModule())
                .build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.exchange_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExchangeViewModel::class.java)
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

    override fun intents(): Observable<ExchangeIntent> {
        view?.run {
            return RxTextView
                    .textChanges(this.exchangeValueToConvertView)
                    .filter { value -> value.isNotEmpty() }
                    .switchMap { value ->
                        RxTextView
                                .textChanges(this.exchangeCurrencyFromView)
                                .filter { fromCurrency -> fromCurrency.length == 3 }
                                .switchMap { fromCurrency ->
                                    RxTextView
                                            .textChanges(this.exchangeCurrencyToView)
                                            .filter { toCurrency -> toCurrency.length == 3 }
                                            .debounce(500, TimeUnit.MILLISECONDS)
                                            .map { toCurrency ->
                                                LoadExchangeIntent(fromCurrency.toString(), toCurrency.toString(), value.toString().toFloat())
                                            }
                                }
                    }
        }
        return Observable.empty()
    }

    override fun render(state: ExchangeState?) {
        renderer.render(state, this)
    }
}