package com.xmarcusv.moviex.ui.list

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.xmarcusv.moviex.R
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.util.Constants

class MovieRowViewModel(private val movie: Movie) {

    val selectedMovieLiveData = MutableLiveData<Pair<View, Movie>>()

    fun getTitle(): String = movie.originalTitle

    fun onMovieClicked(view: View) {
        selectedMovieLiveData.value = Pair(view, movie)
    }

    fun getPosterUrl(): String {
        return Constants.IMAGE_URL.format(Constants.POSTER_SIZE, movie.posterPath)
    }

    fun getPosterOptions(): RequestOptions {
        return RequestOptions().placeholder(R.drawable.ic_video_player_loading).error(R.drawable.ic_video_player_error)
    }
}