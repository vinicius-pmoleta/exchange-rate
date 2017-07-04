package com.exchangerate.core

import android.app.Application
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.core.di.component.DaggerApplicationComponent
import com.exchangerate.core.di.module.ApplicationModule
import com.exchangerate.core.di.module.NetworkModule
import com.exchangerate.core.di.module.RepositoryModule
import com.exchangerate.core.di.module.SchedulerModule

class ExchangeRateApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initializeDependencyInjection()
    }

    private fun initializeDependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .repositoryModule(RepositoryModule())
                .schedulerModule(SchedulerModule())
                .build()
    }

}
