package com.sohnyi.moviedb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sohnyi.moviedb.api.TMDBService
import com.sohnyi.moviedb.model.Movie
import com.sohnyi.moviedb.network.Constant
import com.sohnyi.moviedb.network.NetworkRepository
import kotlinx.coroutines.flow.Flow

private const val TAG = "TMDBRepository"
/**
 * 仓库类，用于获取数据
 */
class TMDBRepository {

    private val service: TMDBService = NetworkRepository.obtainRetrofitService(TMDBService::class.java)


    fun getTopRatedMoviesStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constant.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TMDBPagingSource(service)
            }
        ).flow
    }
}