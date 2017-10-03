package com.xmarcusv.moviex.ui

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), Observable {

    protected var compositeDisposable = CompositeDisposable()

    @Transient private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks?.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.remove(callback)
    }

    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, fieldId, null)
    }

    override fun onCleared() {
        super.onCleared()
        mCallbacks?.clear()
        compositeDisposable.clear()
    }
}