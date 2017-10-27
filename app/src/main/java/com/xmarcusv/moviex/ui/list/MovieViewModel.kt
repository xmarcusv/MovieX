package com.xmarcusv.moviex.ui.list

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import com.xmarcusv.moviex.base.Resource
import com.xmarcusv.moviex.base.Status
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.MovieListType
import com.xmarcusv.moviex.repository.MovieRepository
import com.xmarcusv.moviex.base.BaseViewModel
import javax.inject.Inject

class MovieViewModel @Inject constructor(private var movieRepository: MovieRepository) : BaseViewModel() {

    val mostPopularList = MutableLiveData<Resource<List<Movie>>>()
    val topRatedList = MutableLiveData<Resource<List<Movie>>>()
    val upcomingList = MutableLiveData<Resource<List<Movie>>>()

    val mostPopularLoadingVisibility = ObservableField<Int>(View.GONE)
    val topRatedLoadingVisibility = ObservableField<Int>(View.GONE)
    val upcomingLoadingVisibility = ObservableField<Int>(View.GONE)

    init {
        load(MovieListType.POPULAR, mostPopularList, mostPopularLoadingVisibility)
        load(MovieListType.TOP_RATED, topRatedList, topRatedLoadingVisibility)
        load(MovieListType.UPCOMING, upcomingList, upcomingLoadingVisibility)
    }

    private fun load(movieListType: MovieListType, list: MutableLiveData<Resource<List<Movie>>>, loading: ObservableField<Int>) {
        compositeDisposable
                .add(movieRepository
                        .getMovies(movieListType = movieListType)
                        .subscribe({ resource: Resource<List<Movie>>? ->
                            list.value = resource
                            loading.set(getLoadingVisibilityByList(list))
                        }))
    }

    private fun getLoadingVisibilityByList(list: MutableLiveData<Resource<List<Movie>>>): Int {
        if (list.value?.status == Status.LOADING) return View.VISIBLE
        return View.GONE
    }
}