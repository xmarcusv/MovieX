package com.mbarcelos.avenuecode.kotlintest.api

import android.arch.lifecycle.LiveData
import com.mbarcelos.avenuecode.kotlintest.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{path}/")
    fun getMovies(@Path("path") type: String, @Query("page") login: Int = 1, @Query("language") language: String = "en-US"): LiveData<ApiResponse<MoviesResponse>>
}