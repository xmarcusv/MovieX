package com.mbarcelos.avenuecode.kotlintest.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mbarcelos.avenuecode.kotlintest.ui.movie.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: MovieViewModelFactory): ViewModelProvider.Factory
}