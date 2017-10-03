package com.xmarcusv.moviex.ui.details

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xmarcusv.moviex.di.Injectable
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.R
import com.xmarcusv.moviex.databinding.ActivityDetailsBinding
import org.parceler.Parcels
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity(), Injectable {

    companion object {
        private val MOVIE_PARAM = "movie"

        fun startActivity(context: Context, selectedMovie: Movie) {
            context.startActivity(Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_PARAM, Parcels.wrap(selectedMovie))
            })
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java) }

    private var selectedMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        binding.viewModel = viewModel

        selectedMovie = Parcels.unwrap(intent.extras.getParcelable(MOVIE_PARAM))

        selectedMovie?.let { viewModel.setMovie(it) }
    }
}