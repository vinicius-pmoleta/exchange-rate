package com.exchangerate.core.data.repository

import io.reactivex.Flowable

interface UsageRepository {

    fun getUsage(): Flowable<String>

}