package com.giovannidiluca.network.retrofit

import com.giovannidiluca.network.model.NewsResponse
import com.giovannidiluca.utils.DefaultValues.Companion.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "29c8cb10ad8e4397a2732a30d24b8e51"

interface NewsApi {
    @GET("top-headlines/")
    suspend fun getHeadlines(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse
}