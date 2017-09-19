package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mbarcelos.avenuecode.kotlintest.model.MOVIE_LIST_TYPE
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.repository.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(var movieRepository: MovieRepository) : ViewModel() {

    var movieListType = MutableLiveData<MOVIE_LIST_TYPE>().apply {
        this.value = MOVIE_LIST_TYPE.POPULAR
    }

    var movieList = MediatorLiveData<List<Movie>>().apply {
        addSource(movieListType, { loadMovies() })
    }

    var loading = MediatorLiveData<Boolean>().apply {
        value = true
        this.addSource(movieList) { this.value = false }
        this.addSource(movieListType) { this.value = true }
    }

    init {
        loadMovies()
    }

    fun loadMovies() {
        movieList.addSource(movieRepository.getMovies(movieListType = movieListType.value!!), {
            it?.body?.results?.let {
                movieList.value = it
            }
        })
    }
}