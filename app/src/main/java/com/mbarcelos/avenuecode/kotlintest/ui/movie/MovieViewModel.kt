package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.mbarcelos.avenuecode.kotlintest.model.MOVIE_LIST_TYPE
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.model.Resource
import com.mbarcelos.avenuecode.kotlintest.repository.MovieRepository
import com.mbarcelos.avenuecode.kotlintest.util.switchMap
import javax.inject.Inject

class MovieViewModel @Inject constructor(private var movieRepository: MovieRepository) : ViewModel() {

    var mostPopularList: LiveData<Resource<List<Movie>>>
    var topRatedList: LiveData<Resource<List<Movie>>>
    var upcomingList: LiveData<Resource<List<Movie>>>

    init {
        loadPopularMovies()
        loadTopRatedMovies()
        loadUpcomingMovies()
    }

    fun loadPopularMovies() {

        mostPopularList = movieRepository.getMovies(movieListType = MOVIE_LIST_TYPE.POPULAR)

        mostPopularList.addSource(movieRepository.getMovies(movieListType = MOVIE_LIST_TYPE.POPULAR), {
            it?.body?.results?.let {
                mostPopularList.value = it
            }
        })
    }

    fun loadTopRatedMovies() {
        topRatedList.addSource(movieRepository.getMovies(movieListType = MOVIE_LIST_TYPE.TOP_RATED), {
            it?.body?.results?.let {
                topRatedList.value = it
            }
        })
    }

    fun loadUpcomingMovies() {
        upcomingList.addSource(movieRepository.getMovies(movieListType = MOVIE_LIST_TYPE.UPCOMING), {
            it?.body?.results?.let {
                upcomingList.value = it
            }
        })
    }
}