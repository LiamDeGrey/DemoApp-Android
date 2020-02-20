package com.liamdegrey.demoapp.helpers.extensions

import androidx.fragment.app.FragmentManager
import com.liamdegrey.demoapp.ui.common.BaseFragment

fun FragmentManager.getTopFragment(): BaseFragment? =
    backStackEntryCount
        .takeIf { it != 0 }
        ?.run {
            getBackStackEntryAt(backStackEntryCount.dec()).name?.let {
                return findFragmentByTag(it) as? BaseFragment
            }
        }