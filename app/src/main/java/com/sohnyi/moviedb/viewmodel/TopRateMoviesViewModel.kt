package com.sohnyi.moviedb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sohnyi.moviedb.data.TMDBRepository
import com.sohnyi.moviedb.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

private const val TAG = "TopRateMoviesViewModel"

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class TopRateMoviesViewModel(
    private val repository: TMDBRepository,
) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<Movie>>


    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val loadMovies = actionStateFlow
            .filterIsInstance<UiAction.LoadMovies>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.LoadMovies) }

        pagingDataFlow = loadMovies
            .flatMapLatest {
                getTopRatedMovies()
            }
            .catch { viewModelScope }
    }


    private fun getTopRatedMovies(): Flow<PagingData<Movie>> =
        repository.getTopRatedMoviesStream()
}

private sealed class UiAction {
    data object LoadMovies : UiAction()
}