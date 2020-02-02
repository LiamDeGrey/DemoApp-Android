package com.liamdegrey.rocketlaunches.ui.main

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.liamdegrey.rocketlaunches.App
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.common.BaseViewModel
import com.liamdegrey.rocketlaunches.ui.detail.DetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(app: Application) : BaseViewModel(app) {
    private val dataBroker by lazy { getApplication<App>().dataBroker }

    val isRefreshing by lazy { MutableLiveData<Boolean>() }
    val rocketLaunches by lazy { MutableLiveData<List<RocketLaunch>>() }
    val errorMessage by lazy { MutableLiveData<String?>() }


    override fun attach(args: Bundle?) {
        super.attach(args)

        setLoading(true)
        refreshData()
    }

    fun refreshData() {
        subscribe(
            dataBroker.getUpcomingRocketLaunches()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    setLoading(false)
                    setRefreshing(false)
                }
                .subscribe({
                    //Loaded items
                    setRocketLaunches(it.rocketLaunches)
                }, {
                    //Failed to load items
                    setErrorMessage(getApplication<App>().getString(R.string.main_errorMessage))
                })
        )
    }

    fun onRocketLaunchViewClicked(position: Int) {
        rocketLaunches.value?.get(position)?.let {
            startFragment(DetailFragment.createFragment(it))
        }
    }

    //region: View methods

    private fun setRefreshing(isRefreshing: Boolean) {
        this.isRefreshing.value = isRefreshing
    }

    private fun setRocketLaunches(rocketLaunches: List<RocketLaunch>) {
        this.rocketLaunches.value = rocketLaunches
    }

    private fun setErrorMessage(errorMessage: String?) {
        this.errorMessage.value = errorMessage
    }

    //endregion
}