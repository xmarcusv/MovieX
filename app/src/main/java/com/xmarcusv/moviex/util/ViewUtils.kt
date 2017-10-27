package com.xmarcusv.moviex.util

import android.annotation.TargetApi
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * Utility methods for working with Views.
 */
object ViewUtils {


    val DRAWABLE_ALPHA = AnimUtils.createIntProperty(object : AnimUtils.IntProp<Drawable>("alpha") {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        override operator fun set(drawable: Drawable, alpha: Int) {
            drawable.alpha = alpha
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        override operator fun get(drawable: Drawable): Int {
            return drawable.alpha
        }
    })
}