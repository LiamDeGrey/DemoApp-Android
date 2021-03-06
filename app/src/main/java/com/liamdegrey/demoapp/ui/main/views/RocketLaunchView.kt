package com.liamdegrey.demoapp.ui.main.views

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.liamdegrey.demoapp.R
import com.liamdegrey.demoapp.network.models.RocketLaunch
import kotlinx.android.synthetic.main.view_rocketlaunch.view.*

class RocketLaunchView private constructor(context: Context) : CardView(context) {

    init {
        View.inflate(context, R.layout.view_rocketlaunch, this)
    }

    class Holder(context: Context) :
        RecyclerView.ViewHolder(RocketLaunchView(context)) {

        init {
            itemView.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        fun populate(rocketLaunch: RocketLaunch) {
            Glide
                .with(itemView)
                .load(rocketLaunch.getPhotoUrl())
                .placeholder(R.drawable.ic_hourglass)
                .error(android.R.drawable.ic_delete)
                .into(itemView.rocketLaunch_imageView)

            val countryCodeOfInterest = rocketLaunch.getLaunchCountryCode()
            itemView.rocketLaunch_nameView.text = rocketLaunch.name
            itemView.rocketLaunch_locationView.text = itemView.context.getString(
                R.string.main_launchedFrom,
                rocketLaunch.launchPad.location.label
            )
            itemView.rocketLaunch_countryCodeView.text = countryCodeOfInterest

            itemView.setBackgroundResource(if (countryCodeOfInterest == null) R.color.white else R.color.fluroYellow)
        }
    }
}