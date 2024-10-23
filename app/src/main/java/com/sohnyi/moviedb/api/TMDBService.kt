package com.sohnyi.moviedb.api

import com.sohnyi.moviedb.model.TopRateResp
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("language") language: String = "zh-CN"
    ): TopRateResp

}