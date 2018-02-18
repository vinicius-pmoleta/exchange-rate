package com.exchangerate.features.conversion.di

import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.conversion.presentation.ConversionFragment
import dagger.Component

@FeatureScope
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(ConversionFeatureModule::class)]
)
interface ConversionFeatureComponent {

    fun inject(fragment: ConversionFragment)

}