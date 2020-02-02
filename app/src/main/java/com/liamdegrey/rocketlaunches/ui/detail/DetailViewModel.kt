package com.liamdegrey.rocketlaunches.ui.detail

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.common.BaseViewModel

class DetailViewModel(app: Application) : BaseViewModel(app) {
    val rocketLaunch by lazy { MutableLiveData<RocketLaunch>() }


    override fun attach(args: Bundle?) {
        super.attach(args)

        args?.getParcelable<RocketLaunch>(RocketLaunch.ARGUMENT_KEY)?.let {
            setRocketLaunch(it)
        }
    }

    //region: View methods

    private fun setRocketLaunch(rocketLaunch: RocketLaunch) {
        this.rocketLaunch.value = rocketLaunch
    }

    //endregion
}