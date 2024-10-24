package com.sohnyi.moviedb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.sohnyi.moviedb.data.TMDBRepository
import com.sohnyi.moviedb.model.Movie

private const val TAG = "TopRateMoviesViewModel"

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class TopRateMoviesViewModel(
    private val repository: TMDBRepository,
) : ViewModel() {



    fun getTopRatedMovies(): LiveData<PagingData<Movie>> =
        repository.getTopRatedMovies()
}