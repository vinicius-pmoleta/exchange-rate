package com.exchangerate.features.usage.di

import com.exchangerate.core.di.ActivityScope
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.features.usage.presentation.UsageFragment
import dagger.Component

@ActivityScope
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                UsageFeatureModule::class,
                UsageUseCasesModule::class
        )
)
interface UsageFeatureComponent {

    fun inject(fragment: UsageFragment)

}