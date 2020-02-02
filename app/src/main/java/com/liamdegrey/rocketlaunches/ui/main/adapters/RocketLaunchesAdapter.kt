package com.liamdegrey.rocketlaunches.ui.main.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.ui.main.views.RocketLaunchView

class RocketLaunchesAdapter(private val callbacks: Callbacks) :
    RecyclerView.Adapter<RocketLaunchView.Holder>(),
    View.OnClickListener {
    interface Callbacks {
        fun onRocketLaunchViewClicked(position: Int)
    }

    private val data = ArrayList<RocketLaunch>()


    fun setData(rocketLaunches: List<RocketLaunch>) {
        data.clear()
        data.addAll(rocketLaunches)

        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RocketLaunchView.Holder(parent.context).also {
            it.itemView.setOnClickListener(this)
        }

    override fun onBindViewHolder(holder: RocketLaunchView.Holder, position: Int) {
        holder.populate(data[position])
        holder.itemView.tag = position
    }

    override fun onClick(v: View) {
        callbacks.onRocketLaunchViewClicked(v.tag as Int)
    }
}