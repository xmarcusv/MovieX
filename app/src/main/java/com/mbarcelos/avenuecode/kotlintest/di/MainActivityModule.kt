package com.mbarcelos.avenuecode.kotlintest.di

import com.mbarcelos.avenuecode.kotlintest.ui.movie.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = arrayOf())
    internal abstract fun contributeMainActivity(): MainActivity
}