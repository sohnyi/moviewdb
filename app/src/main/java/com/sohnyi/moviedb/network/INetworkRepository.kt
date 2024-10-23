package com.sohnyi.moviedb.network

interface INetworkRepository {
    fun <T> obtainRetrofitService(service: Class<T>): T
}