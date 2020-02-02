package com.liamdegrey.rocketlaunches.ui.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.liamdegrey.rocketlaunches.App
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(app: Application) : BaseViewModel(app) {
    private val dataBroker by lazy { getApplication<App>().dataBroker }

    val rocketLaunches by lazy { MutableLiveData<List<RocketLaunch>>() }
    val errorMessage by lazy { MutableLiveData<String?>() }


    override fun attach(args: Bundle?) {
        super.attach(args)

        setLoading(true)
        subscribe(
            dataBroker.getUpcomingRocketLaunches()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { setLoading(false) }
                .subscribe({
                    //Loaded items
                    setRocketLaunches(it.rocketLaunches)
                }, {
                    //Failed to load items
                    setErrorMessage(getApplication<App>().getString(R.string.main_errorMessage))
                    Log.v("debuggered", it.localizedMessage)
                })
        )
    }

    //region: View methods

    private fun setRocketLaunches(rocketLaunches: List<RocketLaunch>) {
        this.rocketLaunches.value = rocketLaunches
    }

    private fun setErrorMessage(errorMessage: String?) {
        this.errorMessage.value = errorMessage
    }

    //endregion
}