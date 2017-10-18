package com.exchangerate.core.data.live

import android.arch.lifecycle.MutableLiveData
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.lang.ref.WeakReference

class LiveDataReactiveConverter {

    companion object {
        fun <T> fromPublisher(publisher: Publisher<T>): LiveResult<T> {
            val liveData = MutableLiveData<T>()
            val liveDataRef = WeakReference(liveData)

            val liveError = MutableLiveData<Throwable>()
            val liveErrorRef = WeakReference(liveError)

            val liveResult = LiveResult<T>(liveData, liveError)
            publisher.subscribe(object : Subscriber<T> {
                override fun onSubscribe(s: Subscription?) {
                    s?.request(java.lang.Long.MAX_VALUE)
                }

                override fun onNext(t: T) {
                    liveDataRef.get()?.postValue(t)
                }

                override fun onError(t: Throwable?) {
                    liveErrorRef.get()?.postValue(t)
                }

                override fun onComplete() {
                }
            })
            return liveResult
        }
    }

}