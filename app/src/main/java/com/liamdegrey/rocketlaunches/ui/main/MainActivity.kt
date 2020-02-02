package com.liamdegrey.rocketlaunches.ui.main

import android.graphics.Rect
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.helpers.extensions.nonNullObserve
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.common.BaseActivity
import com.liamdegrey.rocketlaunches.ui.main.adapters.RocketLaunchesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResId = R.layout.activity_main
    override val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private val rocketLaunchesAdapter by lazy { RocketLaunchesAdapter() }


    override fun onViewCreated() {
        viewModel.rocketLaunches.nonNullObserve(this) {
            setRocketLaunches(it)
        }
        viewModel.errorMessage.observe(this, Observer {
            setErrorMessage(it)
        })

        main_rocketLaunchesList.adapter = rocketLaunchesAdapter
        spaceRocketLaunchesList()

        setSupportActionBar(toolbar)
    }

    //region: Private methods

    private fun spaceRocketLaunchesList() {
        main_rocketLaunchesList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            private val paddingStandard by lazy { resources.getDimensionPixelSize(R.dimen.padding_standard) }

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val isFirstItem = parent.getChildAdapterPosition(view) == 0
                outRect.top = if (isFirstItem) paddingStandard else 0
                outRect.left = paddingStandard
                outRect.right = paddingStandard
                outRect.bottom = paddingStandard
            }
        })
    }

    //endregion

    //region: View methods

    private fun setRocketLaunches(rocketLaunches: List<RocketLaunch>) {
        rocketLaunchesAdapter.setData(rocketLaunches)
    }

    private fun setErrorMessage(errorMessage: String?) {
        val noError = errorMessage == null

        main_rocketLaunchesList.visibility = if (noError) VISIBLE else GONE
        main_errorMessageView.visibility = if (noError) GONE else VISIBLE

        main_errorMessageView.text = errorMessage
    }

    //endregion
}