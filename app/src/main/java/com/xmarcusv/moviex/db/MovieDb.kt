package com.xmarcusv.moviex.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.MovieListTypeRelation

/**
 * Main database description.
 */
@Database(entities = arrayOf(Movie::class, MovieListTypeRelation::class), version = 1)
abstract class MovieDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}