package com.sohnyi.moviedb.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohnyi.moviedb.api.TMDBService
import com.sohnyi.moviedb.model.Movie
import com.sohnyi.moviedb.network.Constant

/**
 * 分页数据源
 * KEY   - Int
 * VALUE - Movie @see [com.sohnyi.moviedb.model.Movie]
 */
private const val TAG = "TMDBPagingSource"

class TMDBPagingSource(
    private val service: TMDBService,
) : PagingSource<Int, Movie>() {

    /**
     * 获取当前页的 key，用于刷新
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: Constant.NETWORK_FIRST_PAGE
        return try {

            Log.i(TAG, "load: page: ${params.key ?: "null"}, size: ${params.loadSize}")
            val response = service.getTopRatedMovies(page)
            val movies = response.results
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                page + 1
            }
            val preKey = if (page == Constant.NETWORK_FIRST_PAGE) {
                null
            } else {
                page - 1
            }
            // 返回结果页面
            LoadResult.Page(
                data = movies,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e(TAG, "load: Error", e)
            // 返回加载错误结果
            LoadResult.Error(e)
        }

    }
}