package com.sohnyi.moviedb.api

import com.sohnyi.moviedb.model.TopRateResp
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    /**
     * 获取 Top Rated Movies
     * @param page 页码
     * @param language 语言
     */
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "zh-CN"
    ): TopRateResp

}