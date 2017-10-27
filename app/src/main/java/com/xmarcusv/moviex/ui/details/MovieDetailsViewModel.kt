package com.xmarcusv.moviex.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.databinding.Bindable
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.repository.MovieRepository
import com.xmarcusv.moviex.base.BaseViewModel
import com.xmarcusv.moviex.util.Constants
import timber.log.Timber
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private var movieRepository: MovieRepository) : BaseViewModel() {

    val selectedMovie = MutableLiveData<Movie>()
    val onImageReady = MutableLiveData<Drawable>()
    val data: LiveData<*>

    init {
        data = Transformations.map(selectedMovie, {
            Timber.d("Transformations")
            notifyChange()
        })
    }

    fun setMovie(movie: Movie) {
        selectedMovie.value = movie

        compositeDisposable.add(movieRepository.getMovieCredits(movie.id).subscribe({
            Timber.d("loaded movie ${it.id}")
        }, {

        }))
    }

    fun getMovie(): Movie? {
        return selectedMovie.value
    }

    @Bindable
    fun getPosterUrl(): String {
        return Constants.IMAGE_URL.format(Constants.POSTER_SIZE, selectedMovie.value?.posterPath)
    }

    @Bindable
    fun getBackdropUrl(): String {
        return Constants.IMAGE_URL.format(Constants.BACKDROP_SIZE, selectedMovie.value?.backdropPath)
    }

    fun getPosterGlideCallback(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                onImageReady.value = resource
                return false
            }
        }
    }
}