package com.exchangerate.features.usage.di

import com.exchangerate.core.di.ActivityScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.usage.mvi.UsageFragment
import dagger.Component

@ActivityScope
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(UsageFeatureModule::class)]
)
interface UsageFeatureComponent {

    fun inject(fragment: UsageFragment)

}