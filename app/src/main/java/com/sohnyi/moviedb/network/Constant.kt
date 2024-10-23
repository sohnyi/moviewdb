package com.sohnyi.moviedb.network

object Constant {

    // 接口地址
    const val BASE_API_URL = "https://api.themoviedb.org/"

    const val BASE_IMAGE_URL = "https://image.tmdb.org/"

    const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMDllZjE1NmU2MTEzNjY2Mjk3MGY0YzU0NzBlODJmMCIsIm5iZiI6MTcyOTQ5NzkzNS41MzExOTEsInN1YiI6IjY3MTYwYThlOTk0MzYzN2NlNTgyYTMxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.6Ff-wqouV8Yq-my-_N434Zas2uCgiQrhNyMzuABEqtM"

    // 链接超时时间
    const val CONNECT_TIMEOUT = 5L

    // 读取超时时间
    const val READ_TIMEOUT = 20L

    const val WRITE_TIMEOUT = 30L

    const val NETWORK_PAGE_SIZE = 40

    const val NETWORK_FIRST_PAGE= 1
}