package com.xmarcusv.moviex.util

import android.os.Build
import android.util.IntProperty
import android.util.Property

/**
 * Utility methods for working with animations.
 */
object AnimUtils {

    /**
     * A delegate for creating a [Property] of `int` type.
     */
    abstract class IntProp<T>(val name: String) {

        abstract operator fun set(any: T, value: Int)
        abstract operator fun get(any: T): Int
    }

    /**
     * The animation framework has an optimization for `Properties` of type
     * `int` but it was only made public in API24, so wrap the impl in our own type
     * and conditionally create the appropriate type, delegating the implementation.
     */
    fun <T> createIntProperty(impl: IntProp<T>): Property<T, Int> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            object : IntProperty<T>(impl.name) {
                override fun get(any: T): Int? {
                    return impl[any]
                }

                override fun setValue(any: T, value: Int) {
                    impl[any] = value
                }
            }
        } else {
            object : Property<T, Int>(Int::class.java, impl.name) {
                override fun get(any: T): Int {
                    return impl[any]
                }

                override fun set(any: T, value: Int) {
                    impl[any] = value
                }
            }
        }
    }
}
