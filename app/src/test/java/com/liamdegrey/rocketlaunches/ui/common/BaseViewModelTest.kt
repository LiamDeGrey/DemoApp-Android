package com.liamdegrey.rocketlaunches.ui.common

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.liamdegrey.rocketlaunches.App
import com.liamdegrey.rocketlaunches.helpers.extensions.mock
import com.liamdegrey.rocketlaunches.helpers.extensions.whenever
import com.liamdegrey.rocketlaunches.rules.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    protected lateinit var mockApp: App

    protected abstract val viewModel: BaseViewModel


    @Before
    @CallSuper
    open fun setup() {
        //No-op
    }

    //region: Protected methods

    protected inline fun <reified T : Any> forceValidServiceResponse(methodCall: Single<T>) {
        whenever(methodCall).thenReturn(Single.just(mock()))
    }

    protected fun <T> forceInvalidServiceResponse(methodCall: Single<T>?) {
        whenever(methodCall).thenReturn(Single.error(Throwable()))
    }

    //endregion
}