package com.liamdegrey.demoapp.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.liamdegrey.demoapp.helpers.extensions.mock
import com.liamdegrey.demoapp.helpers.extensions.whenever
import com.liamdegrey.demoapp.network.models.RocketLaunch
import com.liamdegrey.demoapp.ui.common.BaseViewModelTest
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.never
import org.mockito.Mockito.verify


class DetailViewModelTest : BaseViewModelTest() {
    override val viewModel by lazy { DetailViewModel(mockApp) }


    @Test
    fun `attach() success`() {
        val mockArgs = mock<Bundle>()
        whenever(mockArgs.getParcelable<RocketLaunch>(RocketLaunch.ARGUMENT_KEY)).thenReturn(mock())

        val rocketLaunchObserver: Observer<RocketLaunch> = mock()
        viewModel.rocketLaunch.observeForever(rocketLaunchObserver)

        viewModel.attach(mockArgs)

        verify(rocketLaunchObserver).onChanged(any(RocketLaunch::class.java))
    }

    @Test
    fun `attach() failure`() {
        val rocketLaunchObserver: Observer<RocketLaunch> = mock()
        viewModel.rocketLaunch.observeForever(rocketLaunchObserver)

        viewModel.attach(null)

        verify(rocketLaunchObserver, never()).onChanged(any(RocketLaunch::class.java))
    }
}
