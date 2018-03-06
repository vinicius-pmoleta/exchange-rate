package com.exchangerate.features.history.di

import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.history.presentation.HistoryFragment
import dagger.Component

@FeatureScope
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(HistoryFeatureModule::class)]
)
interface HistoryFeatureComponent {

    fun inject(fragment: HistoryFragment)
}