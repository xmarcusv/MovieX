package com.mbarcelos.avenuecode.kotlintest.repository

import com.mbarcelos.avenuecode.kotlintest.api.MovieService
import com.mbarcelos.avenuecode.kotlintest.model.MOVIE_LIST_TYPE
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.model.Resource
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieService: MovieService) {

    fun getMovies(login: Int = 1, language: String = "en-US", movieListType: MOVIE_LIST_TYPE = MOVIE_LIST_TYPE.POPULAR): Flowable<Resource<List<Movie>>> {

        return object : NetworkProcessor<List<Movie>>() {
            override fun createCall(): Flowable<List<Movie>> {
                return movieService.getMovies(movieListType.path, login, language).map { result -> result.results }
            }

        }.asFlowable().observeOn(AndroidSchedulers.mainThread())
    }
}