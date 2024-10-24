package com.sohnyi.moviedb.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sohnyi.moviedb.api.TMDBService
import com.sohnyi.moviedb.model.Movie
import com.sohnyi.moviedb.network.Constant
import com.sohnyi.moviedb.network.NetworkRepository

private const val TAG = "TMDBRepository"
/**
 * 仓库类，用于获取数据
 */
class TMDBRepository {

    private val service: TMDBService = NetworkRepository.obtainRetrofitService(TMDBService::class.java)


    fun getTopRatedMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TMDBPagingSource(service)
            }
        ).liveData
    }
}