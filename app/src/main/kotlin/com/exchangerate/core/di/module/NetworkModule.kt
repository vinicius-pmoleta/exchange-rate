package com.exchangerate.core.di.module

import com.exchangerate.BuildConfig
import com.exchangerate.core.ExchangeRateApplication
import com.exchangerate.core.data.repository.local.NetworkCache
import com.exchangerate.core.data.repository.remote.interceptor.AuthenticationInterceptor
import com.exchangerate.core.data.repository.remote.interceptor.OfflineCacheInterceptor
import com.exchangerate.core.data.repository.remote.interceptor.OnlineCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(application: ExchangeRateApplication): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) BODY else NONE

        val builder = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthenticationInterceptor())
                .addInterceptor(OfflineCacheInterceptor(application.applicationContext))
                .addNetworkInterceptor(OnlineCacheInterceptor())
                .cache(NetworkCache(application.applicationContext).initialise())

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://openexchangerates.org/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}