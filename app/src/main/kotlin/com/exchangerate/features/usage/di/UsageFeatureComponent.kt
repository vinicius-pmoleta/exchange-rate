package com.exchangerate.features.usage.di

import com.exchangerate.core.di.FeatureScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.usage.presentation.UsageFragment
import dagger.Component

@FeatureScope
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(UsageFeatureModule::class)]
)
interface UsageFeatureComponent {

    fun inject(fragment: UsageFragment)

}