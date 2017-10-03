package com.mbarcelos.avenuecode.kotlintest.api

import com.mbarcelos.avenuecode.kotlintest.model.MoviesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{path}/")
    fun getMovies(@Path("path") type: String, @Query("page") login: Int = 1, @Query("language") language: String = "en-US"): Flowable<MoviesResponse>
}