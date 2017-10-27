package com.xmarcusv.moviex.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.xmarcusv.moviex.base.MovieViewModelFactory
import com.xmarcusv.moviex.base.ViewModelKey
import com.xmarcusv.moviex.ui.details.MovieDetailsViewModel
import com.xmarcusv.moviex.ui.list.MovieViewModel
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
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    internal abstract fun bindMovieDetailsViewModel(movieViewModel: MovieDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory( Ofactory: MovieViewModelFactory): ViewModelProvider.Factory
}