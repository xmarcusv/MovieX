package com.xmarcusv.moviex.repository

import com.xmarcusv.moviex.api.MovieService
import com.xmarcusv.moviex.model.MOVIE_LIST_TYPE
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.Resource
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