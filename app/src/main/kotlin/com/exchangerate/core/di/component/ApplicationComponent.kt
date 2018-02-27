package com.exchangerate.core.di.component

import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.repository.local.database.ExchangeRateDatabase
import com.exchangerate.core.data.repository.remote.RemoteExchangeRepository
import com.exchangerate.core.di.module.ApplicationModule
import com.exchangerate.core.di.module.LocalRepositoryModule
import com.exchangerate.core.di.module.NetworkModule
import com.exchangerate.core.di.module.RemoteRepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class, NetworkModule::class,
    RemoteRepositoryModule::class, LocalRepositoryModule::class
])
interface ApplicationComponent {

    fun application(): ExchangeRateApplication

    fun remoteRepository(): RemoteExchangeRepository

    fun database(): ExchangeRateDatabase
}