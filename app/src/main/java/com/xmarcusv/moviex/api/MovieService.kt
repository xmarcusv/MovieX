package com.xmarcusv.moviex.api

import com.xmarcusv.moviex.model.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{path}/")
    fun getMovies(@Path("path") type: String, @Query("page") login: Int = 1, @Query("language") language: String = "en-US"): Single<MoviesResponse>
}