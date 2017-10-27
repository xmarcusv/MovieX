package com.xmarcusv.moviex.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 *  view.snack("Snack message") {
 *   action("Action") { toast("Action clicked") }
 *  }
 */
inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) = snack(message, length) {}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, object : Observer<T> {
    override fun onChanged(v: T?) {
        observer.invoke(v)
    }
})

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>) = Transformations.switchMap(this, func)
fun <X, Y> LiveData<X>.map(func: (X) -> Y) = Transformations.map(this, func)


