package com.liamdegrey.rocketlaunches.ui.main.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.main.views.RocketLaunchView

class RocketLaunchesAdapter : RecyclerView.Adapter<RocketLaunchView.Holder>() {
    private val data = ArrayList<RocketLaunch>()


    fun setData(rocketLaunches: List<RocketLaunch>) {
        data.clear()
        data.addAll(rocketLaunches)

        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RocketLaunchView.Holder(parent.context)

    override fun onBindViewHolder(holder: RocketLaunchView.Holder, position: Int) {
        holder.populate(data[position])
    }
}