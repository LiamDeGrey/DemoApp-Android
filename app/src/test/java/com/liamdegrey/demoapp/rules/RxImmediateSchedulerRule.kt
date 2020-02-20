package com.liamdegrey.demoapp.rules

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


/**
 * https://github.com/Monnoroch/android-testing/blob/master/testing/src/test/java/com/testing/rules/RxImmediateSchedulerRule.java
 *
 * A class that changes RxJava schedulers to Immediate and Test schedulers for immediate actions in
 * tests and waiting timeouts for testing RxJava timeout in host java tests.
 */
class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, description: Description?) =
        object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { testScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { IMMEDIATE_SCHEDULER }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }

    companion object {
        /**
         * Get test scheduler for testing RxJava timeout.
         *
         * @return [TestScheduler] object for testing RxJava timeout.
         */
        val testScheduler = TestScheduler()
        private val IMMEDIATE_SCHEDULER: Scheduler = object : Scheduler() {

            override fun scheduleDirect(
                run: Runnable,
                delay: Long,
                unit: TimeUnit
            ) = super.scheduleDirect(run, 0, unit)

            override fun createWorker() =
                ExecutorScheduler.ExecutorWorker(Executor { obj: Runnable -> obj.run() })
        }
    }
}