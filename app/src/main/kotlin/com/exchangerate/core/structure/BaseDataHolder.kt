package com.exchangerate.core.structure

import android.arch.lifecycle.ViewModel
import org.reactivestreams.Subscription

abstract class BaseDataHolder : ViewModel() {

    private val subscriptions = mutableListOf<Subscription>()

    fun addSubscription(subscription: Subscription) {
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        subscriptions.forEach { subscription ->
            subscription.cancel()
        }
        super.onCleared()
    }

}