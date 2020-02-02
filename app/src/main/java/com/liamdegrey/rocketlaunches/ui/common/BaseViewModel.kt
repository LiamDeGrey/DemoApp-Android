package com.liamdegrey.rocketlaunches.ui.common

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.liamdegrey.rocketlaunches.helpers.livedata.InstantClearLiveData
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val newFragment by lazy { InstantClearLiveData<BaseFragment>() }
    val finishScreen by lazy { InstantClearLiveData<Boolean>() }

    private var subscriptions: MutableList<Disposable>? = null


    open fun attach(args: Bundle?) {
        setLoading(isLoading.value ?: false)
    }

    open fun resume() {
        //No-op
    }

    open fun pause() {
        //No-op
    }

    override fun onCleared() {
        super.onCleared()

        subscriptions
            ?.filter { !it.isDisposed }
            ?.forEach {
                it.dispose()
            }
        subscriptions = null
    }

    //region: Public methods

    open fun consumeBackPress() = false

    //endregion

    //region: Protected methods

    protected fun subscribe(disposable: Disposable) {
        if (subscriptions == null) {
            subscriptions = ArrayList()
        }
        subscriptions?.add(disposable)
    }

    //endregion

    //region: View methods

    protected fun setLoading(loading: Boolean) {
        isLoading.value = loading
    }

    protected fun startFragment(fragment: BaseFragment) {
        newFragment.value = fragment
    }

    protected fun finishScreen() {
        finishScreen.value = true
    }

    //endregion
}