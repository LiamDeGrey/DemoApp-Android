package com.liamdegrey.rocketlaunches.helpers.extensions

import com.liamdegrey.rocketlaunches.helpers.properties.NewValueProperty
import kotlin.properties.Delegates

/**
 * Returns a property delegate for a read/write property that calls a specified callback function when changed.
 * Won't notify when the new value is the same as the existing value
 */
inline fun <T> Delegates.newValueObservable(
    initialValue: T,
    crossinline onNewValue: (value: T) -> Unit
) =
    object : NewValueProperty<T>(initialValue) {
        override fun onNewValue(newValue: T) = onNewValue(newValue)
    }