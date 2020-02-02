package com.liamdegrey.rocketlaunches.ui.detail

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.ui.common.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class DetailFragment : BaseFragment() {
    override val layoutResId = R.layout.fragment_detail
    override val viewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }


    override fun onViewCreated(view: View) {


        setSupportActionBar(toolbar, R.string.detail_title)
    }
}