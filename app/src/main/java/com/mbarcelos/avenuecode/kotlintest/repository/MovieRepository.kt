package com.mbarcelos.avenuecode.kotlintest.repository

import android.arch.lifecycle.LiveData
import com.mbarcelos.avenuecode.kotlintest.api.ApiResponse
import com.mbarcelos.avenuecode.kotlintest.api.MovieService
import com.mbarcelos.avenuecode.kotlintest.model.MOVIE_LIST_TYPE
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.model.MoviesResponse
import com.mbarcelos.avenuecode.kotlintest.model.Resource
import com.mbarcelos.avenuecode.kotlintest.util.map
import com.mbarcelos.avenuecode.kotlintest.util.switchMap
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieService: MovieService) {

    fun getMovies(login: Int = 1, language: String = "en-US", movieListType: MOVIE_LIST_TYPE = MOVIE_LIST_TYPE.POPULAR): LiveData<Resource<List<Movie>>> {
        return movieService.getMovies(movieListType.path, login, language).map {
            if (it != null) {
                Resource.success(it.body?.results)
            } else {
                Resource.error(it.errorMessage, null)
            }
        }
    }
}