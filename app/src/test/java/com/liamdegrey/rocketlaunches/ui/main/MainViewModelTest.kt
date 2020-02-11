package com.liamdegrey.rocketlaunches.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.liamdegrey.rocketlaunches.App
import com.liamdegrey.rocketlaunches.network.brokers.DataBroker
import com.liamdegrey.rocketlaunches.network.models.UpcomingRocketLaunches
import com.liamdegrey.rocketlaunches.rules.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
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
        MockitoAnnotations.initMocks(this)

        whenever(mockApp.dataBroker).thenReturn(mockDataBroker)
    }

    @Test
    fun `attach() starts loading`() {
        setValidDataBrokerGetUpcomingRocketLaunchesResponse()

        viewModel.attach(null)

        assert(viewModel.isLoading.value == false)
    }

    //region: Private methods

    private fun setValidDataBrokerGetUpcomingRocketLaunchesResponse() =
        whenever(mockDataBroker.getUpcomingRocketLaunches())
            .thenReturn(
                Single.just(
                    UpcomingRocketLaunches(emptyList())
                )
            )

    //endregion
}