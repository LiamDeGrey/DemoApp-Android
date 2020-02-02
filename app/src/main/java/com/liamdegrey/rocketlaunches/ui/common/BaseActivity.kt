package com.liamdegrey.rocketlaunches.ui.common

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.helpers.extensions.getTopFragment
import com.liamdegrey.rocketlaunches.helpers.extensions.nonNullObserve
import com.liamdegrey.rocketlaunches.ui.common.views.LoadingView

abstract class BaseActivity : AppCompatActivity() {
    @get:LayoutRes
    protected abstract val layoutResId: Int
    protected abstract val viewModel: BaseViewModel

    private var isLoading: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        viewModel.isLoading.nonNullObserve(this) {
            setLoading(it)
        }

        viewModel.newFragment.nonNullObserve(this) {
            startFragment(it)
        }

        viewModel.finishScreen.nonNullObserve(this) {
            finishActivity()
        }

        onViewCreated()


        viewModel.attach(intent.extras)
    }

    protected abstract fun onViewCreated()

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    override fun onResume() {
        super.onResume()

        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()

        viewModel.pause()
    }

    override fun onBackPressed() {
        if (isLoading) {
            return
        }

        supportFragmentManager.getTopFragment()?.also {
            it.takeIf { it.consumeBackPress() }
                ?: run { super.onBackPressed() }
        } ?: run {
            if (!viewModel.consumeBackPress()) super.onBackPressed()
        }
    }

    override fun startActivity(intent: Intent) {
        startActivityAsRoot(intent)
    }

    //region: Private methods

    private fun startActivityAsRoot(intent: Intent) {
        super.startActivity(intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    //endregion

    //region: View methods

    private fun setLoading(loading: Boolean) {
        findViewById<LoadingView>(R.id.loadingView)?.let { loadingView ->
            isLoading = loading
            loadingView.setLoading(loading)
        }
    }

    internal fun startFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_animate_in, 0, 0, R.anim.fragment_animate_out)
            .add(android.R.id.content, fragment, fragment.fragmentTag)
            .addToBackStack(fragment.fragmentTag)
            .commit()
    }

    internal fun popFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun finishActivity() {
        finish()
    }

    //endregion
}