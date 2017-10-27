package com.xmarcusv.moviex.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import paperparcel.PaperParcel

@Entity(tableName = "movie_list")
data class MovieListTypeRelation(
        @PrimaryKey(autoGenerate = true) val id: Long?,

        @ColumnInfo(name = "list_type")
        val listType: String,

        @ColumnInfo(name = "movie_id")
        val movieId: Int)

@Entity(tableName = "movies")
@PaperParcel
data class Movie(
        @PrimaryKey
        @Json(name = "id")
        var id: Int,

        @Json(name = "vote_count")
        @ColumnInfo(name = "vote_count")
        var voteCount: Int,

        @Json(name = "video")
        var video: Boolean,

        @Json(name = "vote_average")
        @ColumnInfo(name = "vote_average")
        var voteAverage: Double,

        @Json(name = "title")
        var title: String,

        @Json(name = "popularity")
        var popularity: Double,

        @Json(name = "poster_path")
        @ColumnInfo(name = "poster_path")
        var posterPath: String,

        @Json(name = "original_language")
        @ColumnInfo(name = "original_language")
        var originalLanguage: String,

        @Json(name = "original_title")
        @ColumnInfo(name = "original_title")
        var originalTitle: String,

        /*@Json(name = "genre_ids")
        @Ignore
        var genreIds: List<Int>,*/

        @Json(name = "backdrop_path")
        @ColumnInfo(name = "backdrop_path")
        var backdropPath: String,

        @Json(name = "adult")
        var adult: Boolean,

        @Json(name = "overview")
        var overview: String,

        @Json(name = "release_date")
        @ColumnInfo(name = "release_date")
        var releaseDate: String
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelMovie.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelMovie.writeToParcel(this, dest, flags)
    }
}

data class Cast(
        @Json(name = "cast_id")
        var castId: Int? = null,
        @Json(name = "character")
        var character: String? = null,
        @Json(name = "credit_id")
        var creditId: String? = null,
        @Json(name = "gender")
        var gender: Int? = null,
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "order")
        var order: Int? = null,
        @Json(name = "profile_path")
        var profilePath: Any? = null)

data class Crew(
        @Json(name = "credit_id")
        var creditId: String? = null,
        @Json(name = "department")
        var department: String? = null,
        @Json(name = "gender")
        var gender: Int? = null,
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "job")
        var job: String? = null,
        @Json(name = "name")
        var name: String? = null,
        @Json(name = "profile_path")
        var profilePath: Any? = null)

data class MovieCredits(
        @Json(name = "id")
        var id: Int? = null,
        @Json(name = "cast")
        var cast: List<Cast>? = null,
        @Json(name = "crew")
        var crew: List<Crew>? = null)

data class MoviesResponse(
        var page: Int,

        @Json(name = "total_results")
        var totalResults: Long,

        @Json(name = "total_pages")
        var totalPages: Int,

        @ColumnInfo(name = "movies")
        var results: List<Movie>)

enum class MovieListType(val path: String) { POPULAR("popular"), TOP_RATED("top_rated"), UPCOMING("upcoming") }