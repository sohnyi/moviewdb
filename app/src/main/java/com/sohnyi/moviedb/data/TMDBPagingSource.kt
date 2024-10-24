package com.sohnyi.moviedb.data

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
        // 获取锚点
        val anchorPosition = state.anchorPosition ?: return null
        // 获取锚点最近的 Page
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null
        // 根据最近 Page 的 key 返回
        return closestPage.prevKey?.plus(1) ?: closestPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        // 获取页码
        // 首次调用时 params 的 key 为 null, 此时使用默认的起始页码 Constant.NETWORK_FIRST_PAGE
        val page = params.key ?: Constant.NETWORK_FIRST_PAGE

        return try {
            // 调用接口获取数据.
            // 如果接口中有 pageSize 等每页数量的参数, 此处使用 params.loadSize 作为入参
            val response = service.getTopRatedMovies(page)
            val movies = response.results
            // 下一页的页码
            val nextKey = if (movies.isEmpty()) {
                // 没有更多的数据了, 则返回 null
                null
            } else {
                // 否则返回当前页码加一
                page + 1
            }
            val preKey = if (page == Constant.NETWORK_FIRST_PAGE) {
                // 如果当前页是第一页, 则返回 null
                null
            } else {
                // 否则返回当前页码减一
                page - 1
            }
            // 返回结果页面
            LoadResult.Page(
                data = movies,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // 返回加载错误结果
            LoadResult.Error(e)
        }
    }
}