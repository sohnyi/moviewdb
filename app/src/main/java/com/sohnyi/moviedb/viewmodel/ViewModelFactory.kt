package com.sohnyi.moviedb.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.sohnyi.moviedb.data.TMDBRepository

class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: TMDBRepository,
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        if (modelClass.isAssignableFrom(TopRateMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopRateMoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}