package com.liamdegrey.rocketlaunches.ui.main.views

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import kotlinx.android.synthetic.main.view_rocketlaunch.view.*

class RocketLaunchView private constructor(context: Context) : ConstraintLayout(context) {

    init {
        View.inflate(context, R.layout.view_rocketlaunch, this)
    }

    class Holder(context: Context) : RecyclerView.ViewHolder(RocketLaunchView(context)) {

        init {
            itemView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        fun populate(rocketLaunch: RocketLaunch) {
            itemView.rocketLaunch_nameView.text = rocketLaunch.name
        }
    }
}