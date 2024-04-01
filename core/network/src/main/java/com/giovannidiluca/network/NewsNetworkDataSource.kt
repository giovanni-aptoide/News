package com.giovannidiluca.network

interface NewsNetworkDataSource {
    fun getRemoteArticles(sourceId: String): NewsPagingSource
}