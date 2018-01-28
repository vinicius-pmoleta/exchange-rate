package com.exchangerate.core.data.live

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

class LiveDataReactiveConverter {

    companion object {
        fun <S> fromPublisher(observable: Observable<S>): LiveData<S> {
            val liveData = MutableLiveData<S>()
            val liveDataRef = WeakReference(liveData)

            observable.subscribe(object : Observer<S> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: S) {
                    liveDataRef.get()?.postValue(t)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })
            return liveData
        }
    }

}