package com.xmarcusv.moviex.di

import com.xmarcusv.moviex.ui.details.MovieDetailsActivity
import com.xmarcusv.moviex.ui.list.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeMovieDetailsActivity(): MovieDetailsActivity
}