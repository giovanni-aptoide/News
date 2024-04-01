package com.giovannidiluca.news.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.giovannidiluca.data.NewsRepositoryImpl
import com.giovannidiluca.data.model.Article
import com.giovannidiluca.data.utils.toArticle
import com.giovannidiluca.news.BuildConfig
import com.giovannidiluca.utils.DefaultValues.Companion.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepositoryImpl
) : ViewModel() {

    fun fetchArticles(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { newsRepository.getArticles(BuildConfig.SOURCE_ID) }
        )
            .flow
            .map { pagingData ->
                pagingData
                    .map { articleResponse -> articleResponse.toArticle() }
            }
            .cachedIn(viewModelScope)
    }
}