package com.exchangerate.core

import android.app.Application
import com.exchangerate.core.di.component.ApplicationComponent
import com.exchangerate.core.di.component.DaggerApplicationComponent
import com.exchangerate.core.di.module.ApplicationModule
import com.exchangerate.core.di.module.NetworkModule
import com.exchangerate.core.di.module.RemoteRepositoryModule
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

class ExchangeRateApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        initializeAnalysis()
        initializeDependencyInjection()
    }

    private fun initializeAnalysis() {
        LeakCanary.install(this)
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeDependencyInjection() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .networkModule(NetworkModule())
                .remoteRepositoryModule(RemoteRepositoryModule())
                .build()
    }

}