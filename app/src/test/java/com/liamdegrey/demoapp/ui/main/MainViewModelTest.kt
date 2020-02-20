package com.liamdegrey.demoapp.ui.main

import androidx.lifecycle.Observer
import com.liamdegrey.demoapp.R
import com.liamdegrey.demoapp.helpers.extensions.mock
import com.liamdegrey.demoapp.helpers.extensions.whenever
import com.liamdegrey.demoapp.network.brokers.DataBroker
import com.liamdegrey.demoapp.network.models.RocketLaunch
import com.liamdegrey.demoapp.ui.common.BaseFragment
import com.liamdegrey.demoapp.ui.common.BaseViewModelTest
import com.liamdegrey.demoapp.ui.detail.DetailFragment
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*


class MainViewModelTest : BaseViewModelTest() {
    @Mock
    private lateinit var mockDataBroker: DataBroker

    override val viewModel by lazy { MainViewModel(mockApp) }


    override fun setup() {
        super.setup()

        whenever(mockApp.dataBroker).thenReturn(mockDataBroker)
    }

    @Test
    fun `attach()`() {
        forceValidServiceResponse(mockDataBroker.getUpcomingRocketLaunches())

        val isLoadingObserver: Observer<Boolean> = mock()
        viewModel.isLoading.observeForever(isLoadingObserver)

        viewModel.attach(null)

        verify(isLoadingObserver).onChanged(true)
    }

    @Test
    fun `refreshData() success`() {
        forceValidServiceResponse(mockDataBroker.getUpcomingRocketLaunches())

        val isLoadingObserver: Observer<Boolean> = mock()
        viewModel.isLoading.observeForever(isLoadingObserver)

        val isRefreshingObserver: Observer<Boolean> = mock()
        viewModel.isRefreshing.observeForever(isRefreshingObserver)

        val rocketLaunchesObserver: Observer<List<RocketLaunch>> = mock()
        viewModel.rocketLaunches.observeForever(rocketLaunchesObserver)

        val errorMessageObserver: Observer<String?> = mock()
        viewModel.errorMessage.observeForever(errorMessageObserver)

        viewModel.attach(null)

        //isLoading
        val inOrder = inOrder(isLoadingObserver)
        inOrder.verify(isLoadingObserver).onChanged(true)
        inOrder.verify(isLoadingObserver).onChanged(false)

        //isRefreshing
        verify(isRefreshingObserver).onChanged(false)

        //rocketLaunches
        verify(rocketLaunchesObserver).onChanged(anyList())

        //errorMessage
        verify(errorMessageObserver, never()).onChanged(anyString())
    }

    @Test
    fun `refreshData() failure`() {
        val errorMessage = "Error message"
        whenever(mockApp.getString(R.string.main_errorMessage)).thenReturn(errorMessage)
        forceInvalidServiceResponse(mockDataBroker.getUpcomingRocketLaunches())

        val isLoadingObserver: Observer<Boolean> = mock()
        viewModel.isLoading.observeForever(isLoadingObserver)

        val isRefreshingObserver: Observer<Boolean> = mock()
        viewModel.isRefreshing.observeForever(isRefreshingObserver)

        val rocketLaunchesObserver: Observer<List<RocketLaunch>> = mock()
        viewModel.rocketLaunches.observeForever(rocketLaunchesObserver)

        val errorMessageObserver: Observer<String?> = mock()
        viewModel.errorMessage.observeForever(errorMessageObserver)

        viewModel.refreshData()

        //isLoading
        verify(isLoadingObserver).onChanged(false)

        //isRefreshing
        verify(isRefreshingObserver).onChanged(false)

        //rocketLaunches
        verify(rocketLaunchesObserver, never()).onChanged(anyList())

        //errorMessage
        verify(errorMessageObserver).onChanged(errorMessage)
    }

    @Test
    fun `onRocketLaunchViewClicked() success`() {
        val newFragmentObserver: Observer<BaseFragment> = mock()
        viewModel.newFragment.observeForever(newFragmentObserver)
        viewModel.rocketLaunches.value = listOf(mock())

        viewModel.onRocketLaunchViewClicked(0)

        //newFragment
        verify(newFragmentObserver).onChanged(any(DetailFragment::class.java))
    }

    @Test
    fun `onRocketLaunchViewClicked() failure`() {
        val newFragmentObserver: Observer<BaseFragment> = mock()
        viewModel.newFragment.observeForever(newFragmentObserver)

        viewModel.onRocketLaunchViewClicked(0)

        //newFragment
        verify(newFragmentObserver, never()).onChanged(any(DetailFragment::class.java))
    }
}