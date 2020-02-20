package com.liamdegrey.demoapp.helpers.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.nonNullObserve(
    owner: LifecycleOwner,
    crossinline observer: (t: T) -> Unit
) {
    observe(owner, Observer {
        it?.let(observer)
    })
}