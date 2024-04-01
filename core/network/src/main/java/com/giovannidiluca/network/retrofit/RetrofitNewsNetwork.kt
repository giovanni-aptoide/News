package com.giovannidiluca.network.retrofit

import com.giovannidiluca.network.NewsNetworkDataSource
import com.giovannidiluca.network.NewsPagingSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNewsNetwork @Inject constructor() : NewsNetworkDataSource {
    private val networkApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)

    override fun getRemoteArticles(sourceId: String): NewsPagingSource =
        NewsPagingSource(backend = networkApi, sourceId = sourceId)

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}