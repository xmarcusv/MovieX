package com.mbarcelos.avenuecode.kotlintest.ui.movie

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mbarcelos.avenuecode.kotlintest.R
import com.mbarcelos.avenuecode.kotlintest.di.Injectable
import com.mbarcelos.avenuecode.kotlintest.model.MOVIE_LIST_TYPE
import com.mbarcelos.avenuecode.kotlintest.model.Movie
import com.mbarcelos.avenuecode.kotlintest.util.snack
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleRegistryOwner, Injectable {

    internal val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
    var movieListAdapter = MovieListAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel::class.java) }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_popular -> {
                movieViewModel.movieListType.postValue(MOVIE_LIST_TYPE.POPULAR)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top_rated -> {
                movieViewModel.movieListType.postValue(MOVIE_LIST_TYPE.TOP_RATED)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_upcomming -> {
                movieViewModel.movieListType.postValue(MOVIE_LIST_TYPE.UPCOMING)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        with(movies_list) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieListAdapter
        }

        movieListAdapter.moveLiveData.observe(this, Observer<Movie> {
            this@MainActivity.contentView?.snack("worked ${it?.originalTitle}")
        })

        swipe_to_refreesh.setOnRefreshListener {
            movieViewModel.loadMovies()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        observeMovieList()
    }

    private fun observeMovieList() {
        movieViewModel.loading.observe(this, Observer<Boolean> {
            it?.let { swipe_to_refreesh.isRefreshing = it }
        })

        movieViewModel.movieList.observe(this, Observer<List<Movie>?> {
            it?.let { movieListAdapter.update(it) }
        })

    }

}