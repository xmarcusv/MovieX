package com.mbarcelos.avenuecode.kotlintest.di

import com.mbarcelos.avenuecode.kotlintest.ui.details.MovieDetailsActivity
import com.mbarcelos.avenuecode.kotlintest.ui.list.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeMovieDetailsActivity(): MovieDetailsActivity
}