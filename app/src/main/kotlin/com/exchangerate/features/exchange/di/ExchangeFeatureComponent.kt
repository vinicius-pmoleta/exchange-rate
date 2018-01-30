package com.exchangerate.features.exchange.di

import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.exchange.presentation.ExchangeFragment
import dagger.Component

@FeatureScope
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(ExchangeFeatureModule::class)]
)
interface ExchangeFeatureComponent {

    fun inject(fragment: ExchangeFragment)

}