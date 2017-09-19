package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mbarcelos.avenuecode.kotlintest.di.Injectable

class MovieDetailsActivitiy : AppCompatActivity(), LifecycleRegistryOwner, Injectable {

    internal val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}