package com.liamdegrey.rocketlaunches.helpers.extensions

import androidx.fragment.app.FragmentManager
import com.liamdegrey.rocketlaunches.ui.common.BaseFragment

fun FragmentManager.getTopFragment(): BaseFragment? =
    backStackEntryCount
        .takeIf { it != 0 }
        ?.run {
            getBackStackEntryAt(backStackEntryCount.dec()).name?.let {
                return findFragmentByTag(it) as? BaseFragment
            }
        }