package com.mbarcelos.avenuecode.kotlintest.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mbarcelos.avenuecode.kotlintest.R
import com.mbarcelos.avenuecode.kotlintest.databinding.ActivityMainBinding
import com.mbarcelos.avenuecode.kotlintest.di.Injectable
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.ui.details.MovieDetailsActivity
import com.mbarcelos.avenuecode.kotlintest.util.observe
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java) }

    private var mostPopularAdapter = MovieListAdapter()
    private var topRatedAdapter = MovieListAdapter()
    private var upcomingAdapter = MovieListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = movieViewModel

        initUI(binding)
    }

    private fun initUI(binding: ActivityMainBinding) {
        with(binding) {
            binding.popularList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.popularList.adapter = mostPopularAdapter

            binding.topRatedList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.topRatedList.adapter = topRatedAdapter

            binding.upcomingList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.upcomingList.adapter = upcomingAdapter
        }

        movieViewModel.mostPopularList.observe(this, Observer { it?.data?.let { mostPopularAdapter.update(it) } })
        movieViewModel.topRatedList.observe(this, Observer { it?.data?.let { topRatedAdapter.update(it) } })
        movieViewModel.upcomingList.observe(this, Observer { it?.data?.let { upcomingAdapter.update(it) } })

        mostPopularAdapter.movieLiveData.observe(this, this::openDetailsActivity)
        topRatedAdapter.movieLiveData.observe(this, this::openDetailsActivity)
        upcomingAdapter.movieLiveData.observe(this, this::openDetailsActivity)
    }

    private fun openDetailsActivity(selectedMovie: Movie?) {
        selectedMovie?.let {
            MovieDetailsActivity.startActivity(this, selectedMovie)
        }
    }
}