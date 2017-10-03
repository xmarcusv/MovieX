package com.mbarcelos.avenuecode.kotlintest.ui.list

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.util.Constants

class MovieRowViewModel(private val movie: Movie) {

    val selectedMovieLiveData = MutableLiveData<Movie>()

    fun getTitle(): String = movie.originalTitle

    fun onMovieClicked(view: View) {
        selectedMovieLiveData.value = movie
    }

    fun getPosterUrl(): String {
        return Constants.IMAGE_URL.format(Constants.POSTER_SIZE, movie.posterPath)
    }
}