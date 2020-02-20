package com.liamdegrey.demoapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.liamdegrey.demoapp.R
import com.liamdegrey.demoapp.helpers.extensions.getSimpleDate
import com.liamdegrey.demoapp.helpers.extensions.nonNullObserve
import com.liamdegrey.demoapp.network.models.RocketLaunch
import com.liamdegrey.demoapp.ui.common.BaseFragment
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {
    override val layoutResId = R.layout.fragment_detail
    override val viewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }


    override fun onViewCreated(view: View) {
        viewModel.rocketLaunch.nonNullObserve(this) {
            setRocketLaunch(it)
        }

        setSupportActionBar(toolbar, R.string.detail_title)
    }

    //region: View methods

    private fun setRocketLaunch(rocketLaunch: RocketLaunch) {
        view?.let {
            Glide
                .with(it)
                .load(rocketLaunch.getPhotoUrl())
                .placeholder(R.drawable.ic_hourglass)
                .error(android.R.drawable.ic_delete)
                .into(detail_imageView)
        }
        detail_nameView.text = rocketLaunch.name
        detail_locationValueView.text = rocketLaunch.launchPad.location.label
        detail_statusValueView.text = rocketLaunch.launchStatus.label
        detail_dateValueView.text = rocketLaunch.date.getSimpleDate()
        detail_missionDescriptionView.text = rocketLaunch.mission?.description
    }

    //endregion

    companion object {

        fun createFragment(rocketLaunch: RocketLaunch): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(RocketLaunch.ARGUMENT_KEY, rocketLaunch)
            fragment.arguments = args
            return fragment
        }
    }
}