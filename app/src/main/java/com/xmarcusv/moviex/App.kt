package com.xmarcusv.moviex

import android.app.Activity
import android.app.Application
import com.xmarcusv.moviex.di.DaggerAppComponent
import com.xmarcusv.moviex.di.applyAutoInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        applyAutoInjector()

        Timber.plant(Timber.DebugTree())

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}