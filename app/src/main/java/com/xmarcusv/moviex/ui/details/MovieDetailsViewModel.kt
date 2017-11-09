package com.xmarcusv.moviex.ui.details

import android.databinding.ObservableField
import com.xmarcusv.moviex.base.BaseViewModel
import com.xmarcusv.moviex.base.GlideLiveDataCallback
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.MovieCredits
import com.xmarcusv.moviex.repository.MovieRepository
import com.xmarcusv.moviex.util.Constants
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private var movieRepository: MovieRepository) : BaseViewModel() {

    val selectedMovie = ObservableField<Movie>()
    val movieCredits = ObservableField<MovieCredits>()
    val onImageReady = GlideLiveDataCallback()

    fun setMovie(movie: Movie) {
        selectedMovie.set(movie)

        /*notifyPropertyChanged(BR.backdropUrl)
        notifyPropertyChanged(BR.posterUrl)*/

        compositeDisposable.add(movieRepository.getMovieCredits(movie.id).subscribe({
            Timber.d("loaded movie ${it.id}")
            movieCredits.set(it)
        }, {

        }))
    }

    fun getPosterUrl(): String {
        return Constants.IMAGE_URL.format(Constants.POSTER_SIZE, selectedMovie.get()?.posterPath)
    }

    fun getBackdropUrl(): String {
        return Constants.IMAGE_URL.format(Constants.BACKDROP_SIZE, selectedMovie.get()?.backdropPath)
    }

    fun getPosterGlideCallback(): GlideLiveDataCallback {
        return onImageReady
    }
}