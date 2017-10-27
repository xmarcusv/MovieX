package com.xmarcusv.moviex.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Rect
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.CardView
import android.view.View

class MoviePosterBehavior : CoordinatorLayout.Behavior<CardView>() {

    val SHOW_HIDE_ANIM_DURATION = 200
    val ANIM_STATE_NONE = 0
    val ANIM_STATE_HIDING = 1
    val ANIM_STATE_SHOWING = 2

    private var mAnimState = ANIM_STATE_NONE

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: CardView, dependency: View?): Boolean {
        if (dependency is AppBarLayout) {
            updateVisibilityForAppBarLayout(dependency, child)
        }
        return false
    }

    private fun updateVisibilityForAppBarLayout(appBarLayout: AppBarLayout, child: CardView): Boolean {
        if (!shouldUpdateVisibility(appBarLayout, child)) {
            return false
        }

        val rect = Rect()
        appBarLayout.getGlobalVisibleRect(rect)

        if (rect.bottom <= getMinimumHeightForVisibleOverlappingContent(appBarLayout)) {
            hide(child)
        } else {
            show(child)
        }

        return true
    }

    private fun shouldUpdateVisibility(dependency: View, child: CardView?): Boolean {
        val lp = child!!.layoutParams as CoordinatorLayout.LayoutParams
        return lp.anchorId == dependency.id
    }

    private fun getMinimumHeightForVisibleOverlappingContent(appBarLayout: AppBarLayout): Int {
        val childCount = appBarLayout.childCount
        val lastChildMinHeight = if (childCount >= 1)
            ViewCompat.getMinimumHeight(appBarLayout.getChildAt(childCount - 1))
        else
            0

        return if (lastChildMinHeight != 0) {
            lastChildMinHeight * 2
        } else appBarLayout.height / 3

    }

    private fun show(child: CardView) {
        if (isOrWillBeShown(child)) {
            return
        }

        child.animate().cancel()

        if (shouldAnimateVisibilityChange(child)) {
            mAnimState = ANIM_STATE_SHOWING

            if (child.visibility != View.VISIBLE) {
                // If the view isn't visible currently, we'll animate it from a single pixel
                child.alpha = 0f
                child.scaleY = 0f
                child.scaleX = 0f
            }

            child.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(SHOW_HIDE_ANIM_DURATION.toLong())
                    .setInterpolator(LinearOutSlowInInterpolator())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            child.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            mAnimState = ANIM_STATE_NONE
                            child.visibility = View.VISIBLE
                        }
                    })
        } else {
            child.visibility = View.VISIBLE
            child.alpha = 1f
            child.scaleY = 1f
            child.scaleX = 1f
        }
    }

    private fun hide(child: CardView) {
        if (isOrWillBeHidden(child)) {
            return
        }

        child.animate().cancel()

        if (shouldAnimateVisibilityChange(child)) {
            mAnimState = ANIM_STATE_HIDING

            child.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(SHOW_HIDE_ANIM_DURATION.toLong())
                    .setInterpolator(FastOutLinearInInterpolator())
                    .setListener(object : AnimatorListenerAdapter() {
                        private var mCancelled: Boolean = false

                        override fun onAnimationStart(animation: Animator) {
                            mCancelled = false
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            mCancelled = true
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            child.visibility = View.GONE
                            mAnimState = ANIM_STATE_NONE
                        }
                    })
        } else {
            child.visibility = View.GONE
        }
    }

    private fun isOrWillBeShown(child: CardView): Boolean {
        return if (child.visibility != View.VISIBLE) {
            mAnimState == ANIM_STATE_SHOWING
        } else {
            mAnimState != ANIM_STATE_HIDING
        }
    }

    private fun isOrWillBeHidden(child: CardView): Boolean {
        return if (child.visibility == View.VISIBLE) {
            mAnimState == ANIM_STATE_HIDING
        } else {
            mAnimState != ANIM_STATE_SHOWING
        }
    }

    private fun shouldAnimateVisibilityChange(child: CardView): Boolean {
        return ViewCompat.isLaidOut(child) && !child.isInEditMode()
    }
}