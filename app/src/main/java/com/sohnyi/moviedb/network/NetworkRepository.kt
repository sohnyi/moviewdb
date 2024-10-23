package com.sohnyi.moviedb.network

import com.sohnyi.moviedb.BuildConfig
import com.sohnyi.moviedb.network.Constant.BEARER_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkRepository : INetworkRepository {

    private val mRetrofit: Retrofit by lazy {
        provideRetrofit(provideOkHttpClient())
    }


    /**
     * 根据传入的 Api Service 获得对应的 Retrofit Service
     *
     * @param service API Service Class
     * @param T API Service Class
     *
     * @return Retrofit Service Class
     */
    override fun <T> obtainRetrofitService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constant.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 提供OkHttpClient
     */
    private fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

        return OkHttpClient.Builder()
            .connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }
}

private class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        // Modify the request to add the Authorization token
        val modifiedRequest: Request = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $BEARER_TOKEN")
            .build()

        return chain.proceed(modifiedRequest)

    }

}