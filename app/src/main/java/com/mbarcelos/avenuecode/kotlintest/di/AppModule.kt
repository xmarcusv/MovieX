package com.mbarcelos.avenuecode.kotlintest.di

import dagger.Module

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule