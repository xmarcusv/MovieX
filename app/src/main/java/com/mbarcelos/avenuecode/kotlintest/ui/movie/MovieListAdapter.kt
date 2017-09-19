package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.MediatorLiveData
import com.mbarcelos.avenuecode.kotlintest.R
import com.mbarcelos.avenuecode.kotlintest.databinding.MovieRowBinding
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.ui.DataBindAdapter

class MovieListAdapter : DataBindAdapter<Movie, MovieRowBinding>(R.layout.movie_row) {

    val moveLiveData = MediatorLiveData<Movie>()

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun bind(holder: DataBindViewHolder<MovieRowBinding>, position: Int) {
        holder.binding.viewModel = MovieRowViewModel(items[position]).apply {
            moveLiveData.addSource(selectedMovieLiveData, {
                moveLiveData.value = it
            })
        }
    }

}