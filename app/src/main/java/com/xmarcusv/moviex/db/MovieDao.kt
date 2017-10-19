package com.xmarcusv.moviex.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.MovieListTypeRelation
import io.reactivex.Maybe


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(movies: List<MovieListTypeRelation>)

    @Query("DELETE FROM movie_list where list_type = :movieType")
    fun deleteAllListByType(movieType: String): Int

    @Query("SELECT m.* " +
            "FROM movies as m " +
            "INNER JOIN movie_list as l " +
            "ON m.id = l.movie_id " +
            "WHERE l.list_type = :movieType " +
            "order by l.id")
    fun findMovieByType(movieType: String): Maybe<List<Movie>>
}