package com.xmarcusv.moviex.di

import android.app.Application
import android.arch.persistence.room.Room
import com.xmarcusv.moviex.db.MovieDao
import com.xmarcusv.moviex.db.MovieDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): MovieDb {
        return Room.databaseBuilder(app, MovieDb::class.java, "movie.db").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDb): MovieDao {
        return db.movieDao()
    }
}