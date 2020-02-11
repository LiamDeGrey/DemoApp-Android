package com.liamdegrey.rocketlaunches.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liamdegrey.rocketlaunches.App
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.helpers.extensions.mock
import com.liamdegrey.rocketlaunches.network.brokers.DataBroker
import com.liamdegrey.rocketlaunches.network.models.RocketLaunch
import com.liamdegrey.rocketlaunches.network.models.UpcomingRocketLaunches
import com.liamdegrey.rocketlaunches.rules.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockApp: App
    @Mock
    private lateinit var mockDataBroker: DataBroker

    private val viewModel by lazy { MainViewModel(mockApp) }


    @Before
    fun setup() {
        whenever(mockApp.dataBroker).thenReturn(mockDataBroker)
    }

    @Test
    fun `loading starts and stops`() {
        setValidDataBrokerGetUpcomingRocketLaunchesResponse()

        val isLoadingObserver: Observer<Boolean> = mock()
        viewModel.isLoading.observeForever(isLoadingObserver)

        viewModel.attach(null)

        val inOrder = inOrder(isLoadingObserver)
        inOrder.verify(isLoadingObserver).onChanged(true)
        inOrder.verify(isLoadingObserver).onChanged(false)
    }

    @Test
    fun `refreshing stops`() {
        setValidDataBrokerGetUpcomingRocketLaunchesResponse()

        val isRefreshingObserver: Observer<Boolean> = mock()
        viewModel.isRefreshing.observeForever(isRefreshingObserver)

        viewModel.attach(null)

        verify(isRefreshingObserver).onChanged(false)
    }

    @Test
    fun `refreshData() succeeds`() {
        setValidDataBrokerGetUpcomingRocketLaunchesResponse()

        val rocketLaunchesObserver: Observer<List<RocketLaunch>> = mock()
        viewModel.rocketLaunches.observeForever(rocketLaunchesObserver)

        viewModel.refreshData()

        verify(rocketLaunchesObserver).onChanged(anyList())
    }

    @Test
    fun `refreshData() fails`() {
        val errorMessage = "Error message"
        whenever(mockApp.getString(R.string.main_errorMessage)).thenReturn(errorMessage)
        whenever(mockDataBroker.getUpcomingRocketLaunches()).thenReturn(Single.error(Throwable()))

        val errorMessageObserver: Observer<String?> = mock()
        viewModel.errorMessage.observeForever(errorMessageObserver)

        viewModel.refreshData()

        verify(errorMessageObserver).onChanged(errorMessage)
    }

    //region: Private methods

    private fun setValidDataBrokerGetUpcomingRocketLaunchesResponse() =
        whenever(mockDataBroker.getUpcomingRocketLaunches())
            .thenReturn(
                Single.just(UpcomingRocketLaunches(emptyList()))
            )

    //endregion
}