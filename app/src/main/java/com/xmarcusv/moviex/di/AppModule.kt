package com.xmarcusv.moviex.di

import dagger.Module

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule