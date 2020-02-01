package com.liamdegrey.rocketlaunches.helpers.livedata

import androidx.lifecycle.MutableLiveData

/**
 * A LiveData object that clears the value immediately after setting it.
 * This is useful for instances where a LiveData value should not be
 * persisted e.g. New activity that should only occur once
 */
class InstantClearLiveData<T> : MutableLiveData<T>() {

    override fun setValue(value: T?) {
        value?.let {
            super.setValue(value)
            setValue(null)
        }
    }
}