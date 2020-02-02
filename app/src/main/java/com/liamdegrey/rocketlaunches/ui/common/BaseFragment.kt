package com.liamdegrey.rocketlaunches.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.liamdegrey.rocketlaunches.R
import com.liamdegrey.rocketlaunches.helpers.extensions.nonNullObserve
import com.liamdegrey.rocketlaunches.ui.common.views.LoadingView

abstract class BaseFragment : Fragment() {
    internal val fragmentTag = javaClass.name

    @get:LayoutRes
    protected abstract val layoutResId: Int
    protected abstract val viewModel: BaseViewModel

    private val activity: BaseActivity?
        get() = getActivity() as? BaseActivity
    private var isLoading: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isClickable = true

        viewModel.isLoading.nonNullObserve(this) {
            setLoading(it)
        }

        viewModel.newFragment.nonNullObserve(this) {
            startFragment(it)
        }

        viewModel.finishScreen.nonNullObserve(this) {
            finishFragment()
        }

        onViewCreated(view)
        restoreSavedInstance(savedInstanceState)

        viewModel.attach(arguments)
    }

    protected abstract fun onViewCreated(view: View)

    protected open fun restoreSavedInstance(savedInstanceState: Bundle?) {
        //No-op
    }

    override fun onResume() {
        super.onResume()

        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()

        viewModel.pause()
    }

    internal fun consumeBackPress() =
        isLoading || viewModel.consumeBackPress()

    protected fun setSupportActionBar(toolbar: Toolbar, @StringRes titleRes: Int) {
        activity?.setSupportActionBar(toolbar)
        activity?.supportActionBar?.title = getString(titleRes)
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //region: View methods

    private fun setLoading(loading: Boolean) {
        view?.findViewById<LoadingView>(R.id.loadingView)?.let { loadingView ->
            isLoading = loading
            loadingView.setLoading(loading)
        }
    }

    private fun startFragment(fragment: BaseFragment) {
        activity?.startFragment(fragment)
    }

    private fun finishFragment() {
        activity?.popFragment()
    }

    //endregion
}