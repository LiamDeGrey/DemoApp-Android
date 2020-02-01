package com.liamdegrey.rocketlaunches.helpers.properties

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class NewValueProperty<T>(initialValue: T) : ReadWriteProperty<Any?, T> {
    private var value = initialValue

    /**
     * The callback which is called after the change of the property is made. The value of the property
     * has already been changed when this callback is invoked.
     */
    open fun onNewValue(newValue: T) {}

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value == value) {
            return
        }
        this.value = value
        onNewValue(value)
    }
}