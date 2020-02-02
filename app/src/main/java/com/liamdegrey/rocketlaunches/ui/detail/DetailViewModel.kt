package com.liamdegrey.rocketlaunches.ui.detail

import android.app.Application
import com.liamdegrey.rocketlaunches.ui.common.BaseViewModel

class DetailViewModel(app: Application) : BaseViewModel(app) {


    fun onHomeClicked() {
        finishScreen()
    }
}