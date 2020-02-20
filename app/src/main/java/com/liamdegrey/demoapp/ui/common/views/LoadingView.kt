package com.liamdegrey.demoapp.ui.common.views

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.liamdegrey.demoapp.R

class LoadingView : FrameLayout, Animator.AnimatorListener {
    private val loadingAnimation: ObjectAnimator

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        id = R.id.loadingView
        visibility = View.GONE
        isClickable = true
        setBackgroundResource(R.color.semiTransparent)

        val spinningView = AppCompatImageView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            scaleType = ImageView.ScaleType.CENTER_INSIDE
            setImageResource(R.drawable.ic_refresh)
        }

        loadingAnimation = ObjectAnimator.ofFloat(spinningView, View.ROTATION, 0f, 360f).apply {
            repeatMode = RESTART
            interpolator = AccelerateDecelerateInterpolator()
            duration = ROTATION_DURATION_MILLISECONDS
            addListener(this@LoadingView)
        }

        addView(spinningView)
    }

    fun setLoading(loading: Boolean) {
        if (loading) {
            loadingAnimation.repeatCount = INFINITE
            loadingAnimation.start()
        } else {
            loadingAnimation.repeatCount = 0
        }
    }

    override fun onAnimationStart(animation: Animator?) {
        visibility = VISIBLE
    }

    override fun onAnimationEnd(animation: Animator?) {
        visibility = GONE
    }

    override fun onAnimationCancel(animation: Animator?) {
        visibility = GONE
    }

    override fun onAnimationRepeat(animation: Animator?) {
        //No-op
    }

    companion object {
        private const val ROTATION_DURATION_MILLISECONDS = 500L
    }
}