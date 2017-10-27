package com.xmarcusv.moviex.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import com.xmarcusv.moviex.R
import com.xmarcusv.moviex.databinding.ActivityDetailsBinding
import com.xmarcusv.moviex.base.Injectable
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.ui.widget.MoviePosterBehavior
import com.xmarcusv.moviex.base.observe
import timber.log.Timber
import javax.inject.Inject


class MovieDetailsActivity : AppCompatActivity(), Injectable {

    companion object {
        private val MOVIE_PARAM = "movie"

        fun startActivity(context: Context, selected: Movie, optionsCompat: ActivityOptionsCompat?) {
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_PARAM, selected)
            }

            if (optionsCompat != null) {
                context.startActivity(intent, optionsCompat.toBundle())
            } else {
                context.startActivity(intent)
            }
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

        viewModel.selectedMovie.observe(this, {
            Timber.d("selectedMovie")
        })

        viewModel.data.observe(this, {
            Timber.d("data")
        })

        selectedMovie = intent.extras.getParcelable(MOVIE_PARAM)

        selectedMovie?.let { viewModel.setMovie(it) }

        supportPostponeEnterTransition()

        viewModel.onImageReady.observe(this, Observer {

            supportStartPostponedEnterTransition()

            val layoutParams = binding.posterContainer.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.behavior = MoviePosterBehavior()
        })
    }
}