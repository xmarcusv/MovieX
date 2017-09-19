package com.mbarcelos.avenuecode.kotlintest.model

import com.squareup.moshi.Json

data class Movie(
        @Json(name = "vote_count")
        var voteCount: Int,
        @Json(name = "id")
        var id: Int,
        @Json(name = "video")
        var video: Boolean,
        @Json(name = "vote_average")
        var voteAverage: Double,
        @Json(name = "title")
        var title: String,
        @Json(name = "popularity")
        var popularity: Double,
        @Json(name = "poster_path")
        var posterPath: String,
        @Json(name = "original_language")
        var originalLanguage: String,
        @Json(name = "original_title")
        var originalTitle: String,
        @Json(name = "genre_ids")
        var genreIds: List<Int>,
        @Json(name = "backdrop_path")
        var backdropPath: String,
        @Json(name = "adult")
        var adult: Boolean,
        @Json(name = "overview")
        var overview: String,
        @Json(name = "release_date")
        var releaseDate: String)

data class MoviesResponse(
        var page: Int,
        @Json(name = "total_results")
        var totalResults: Long,
        @Json(name = "total_pages")
        var totalPages: Int,
        var results: List<Movie>)


enum class MOVIE_LIST_TYPE(val path: String) { POPULAR("popular"), TOP_RATED("top_rated"), UPCOMING("upcoming") }