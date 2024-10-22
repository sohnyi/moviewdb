package com.sohnyi.moviedb.model

import com.google.gson.annotations.SerializedName

data class TopRateResp(
    val page: Int,
    @SerializedName("total_results") val total: Int = 0,
    @SerializedName("total_pages") val pages: Int = 0,
    val results: List<Movie> = emptyList(),
)