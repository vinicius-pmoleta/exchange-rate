package com.exchangerate.core.di.module

import com.exchangerate.core.data.usecase.ExecutionConfiguration
import com.exchangerate.core.data.usecase.ExecutionScheduler
import com.exchangerate.core.data.usecase.PostExecutionScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun provideExecutionScheduler(): ExecutionScheduler {
        return ExecutionScheduler(Schedulers.io())
    }

    @Provides
    @Singleton
    fun providePostExecutionScheduler(): PostExecutionScheduler {
        return PostExecutionScheduler(AndroidSchedulers.mainThread())
    }

    @Provides
    @Singleton
    fun provideExecutionConfiguration(executionScheduler: ExecutionScheduler,
                                      postExecutionScheduler: PostExecutionScheduler)
            : ExecutionConfiguration {
        return ExecutionConfiguration(executionScheduler, postExecutionScheduler)
    }

}