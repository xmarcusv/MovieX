package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.LifecycleRegistry
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
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Injectable {

    internal val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java) }

    var mostPopularAdapter = MovieListAdapter()
    var topRatedAdapter = MovieListAdapter()
    var upcomingAdapter = MovieListAdapter()


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

        movieViewModel.mostPopularList.observe(this, Observer { it?.let { mostPopularAdapter.update(it) } })
        movieViewModel.topRatedList.observe(this, Observer { it?.let { topRatedAdapter.update(it) } })
        movieViewModel.upcomingList.observe(this, Observer { it?.let { upcomingAdapter.update(it) } })
    }
}