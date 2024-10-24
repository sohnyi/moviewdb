package com.sohnyi.moviedb.model

import com.google.gson.annotations.SerializedName

/**
 * Top Rated Movies 接口返回实体类
 * @param page 当前请求的页码
 * @param totalResults 数据总数
 * @param totalPages 总页数
 * @param results 数据列表 @see [com.sohnyi.moviedb.model.Movie]
 */
data class TopRateResp(
    val page: Int,
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    val results: List<Movie> = emptyList(),
)