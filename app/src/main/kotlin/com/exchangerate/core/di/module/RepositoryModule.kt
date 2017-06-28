package com.exchangerate.core.di.module

import com.exchangerate.core.data.repository.UsageRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUsageRepository(retrofit: Retrofit): UsageRepository {
        return retrofit.create(UsageRepository::class.java)
    }

}