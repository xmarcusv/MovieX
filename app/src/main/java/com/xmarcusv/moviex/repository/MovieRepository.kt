package com.xmarcusv.moviex.repository

import com.xmarcusv.moviex.api.MovieService
import com.xmarcusv.moviex.db.MovieDao
import com.xmarcusv.moviex.db.MovieDb
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.MovieListType
import com.xmarcusv.moviex.model.MovieListTypeRelation
import com.xmarcusv.moviex.model.Resource
import com.xmarcusv.moviex.util.RateLimiter
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieService: MovieService, private val db: MovieDb, private val movieDao: MovieDao) {

    val rateLimit = RateLimiter<String>(2, TimeUnit.MINUTES)

    fun getMovies(login: Int = 1, language: String = "en-US", movieListType: MovieListType = MovieListType.POPULAR): Flowable<Resource<List<Movie>>> {

        return object : RepositoryProcessor<List<Movie>>() {
            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty() || rateLimit.shouldFetch(movieListType.path)
            }

            override fun loadFromDb(): Maybe<List<Movie>> {
                return movieDao.findMovieByType(movieListType.path)
            }

            override fun saveCallResult(item: List<Movie>) {
                deleteAllAndInsertMovieListRelation(item, movieListType)
            }

            override fun createCall(): Single<List<Movie>> {
                return movieService.getMovies(movieListType.path, login, language).map { result -> result.results }
            }

            override fun onFetchFailed() {
                rateLimit.reset(movieListType.path)
            }

        }.execute().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun deleteAllAndInsertMovieListRelation(item: List<Movie>, movieListType: MovieListType) {
        db.beginTransaction()
        try {
            movieDao.insertMovies(item)
            movieDao.deleteAllListByType(movieListType.path)
            val movieList = item.mapTo(ArrayList()) { MovieListTypeRelation(null, movieListType.path, it.id) }
            movieDao.insertMoviesList(movieList)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}