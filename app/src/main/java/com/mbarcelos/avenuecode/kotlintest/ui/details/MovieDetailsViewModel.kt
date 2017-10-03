package com.mbarcelos.avenuecode.kotlintest.ui.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.Bindable
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.ui.BaseViewModel
import com.mbarcelos.avenuecode.kotlintest.util.Constants
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor() : BaseViewModel() {

    val selectedMovie = MutableLiveData<Movie>()

    init {
        Transformations.map(selectedMovie, {
            notifyChange()
        })
    }

    fun setMovie(movie: Movie) {
        selectedMovie.value = movie
    }

    @Bindable
    fun getPosterUrl(): String {
        return Constants.IMAGE_URL.format(Constants.POSTER_SIZE, selectedMovie.value?.posterPath)
    }

    @Bindable
    fun getBackdropUrl(): String {
        return Constants.IMAGE_URL.format(Constants.BACKDROP_SIZE, selectedMovie.value?.backdropPath)
    }
}