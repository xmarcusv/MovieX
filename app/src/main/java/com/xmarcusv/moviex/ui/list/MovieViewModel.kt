package com.xmarcusv.moviex.ui.list

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import com.xmarcusv.moviex.model.MOVIE_LIST_TYPE
import com.xmarcusv.moviex.model.Movie
import com.xmarcusv.moviex.model.Resource
import com.xmarcusv.moviex.model.Status
import com.xmarcusv.moviex.repository.MovieRepository
import com.xmarcusv.moviex.ui.BaseViewModel
import javax.inject.Inject

class MovieViewModel @Inject constructor(private var movieRepository: MovieRepository) : BaseViewModel() {

    val mostPopularList = MutableLiveData<Resource<List<Movie>>>()
    val topRatedList = MutableLiveData<Resource<List<Movie>>>()
    val upcomingList = MutableLiveData<Resource<List<Movie>>>()

    val mostPopularLoadingVisibility = ObservableField<Int>(View.GONE)
    val topRatedLoadingVisibility = ObservableField<Int>(View.GONE)
    val upcomingLoadingVisibility = ObservableField<Int>(View.GONE)

    init {
        load(MOVIE_LIST_TYPE.POPULAR, mostPopularList, mostPopularLoadingVisibility)
        load(MOVIE_LIST_TYPE.TOP_RATED, topRatedList, topRatedLoadingVisibility)
        load(MOVIE_LIST_TYPE.UPCOMING, upcomingList, upcomingLoadingVisibility)
    }

    private fun load(movieListType: MOVIE_LIST_TYPE, list: MutableLiveData<Resource<List<Movie>>>, loading: ObservableField<Int>) {
        compositeDisposable.add(movieRepository.getMovies(movieListType = movieListType).subscribe({ resource: Resource<List<Movie>>? ->
            list.value = resource
            loading.set(getLoadingVisibilityByList(list))
        }))
    }

    private fun getLoadingVisibilityByList(list: MutableLiveData<Resource<List<Movie>>>): Int {
        if (list.value?.status == Status.LOADING) return View.VISIBLE
        return View.GONE
    }
}