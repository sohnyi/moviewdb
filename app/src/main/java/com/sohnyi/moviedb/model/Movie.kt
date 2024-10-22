package com.sohnyi.moviedb.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,

    val title: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("vote_average")
    val rate: Float,
)