package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.mbarcelos.avenuecode.kotlintest.model.Movie

class MovieRowViewModel(private val movie: Movie) {

    val selectedMovieLiveData = MutableLiveData<Movie>()

    fun getTitle(): String = movie.originalTitle

    fun onMovieClicked(view: View) {
        selectedMovieLiveData.value = movie
    }

    fun getImageUrl(): String {
        return "https://image.tmdb.org/t/p/w154${movie.posterPath}"
    }
}