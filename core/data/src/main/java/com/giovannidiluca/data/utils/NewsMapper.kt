package com.giovannidiluca.data.utils

import com.giovannidiluca.data.model.Article
import com.giovannidiluca.network.model.ArticleResponse
import com.giovannidiluca.utils.DateFormatterUtils


private const val imageFallback =
    "https://clarionhealthcare.com/wp-content/uploads/2020/12/default-fallback-image.png"
private const val removedTag = "[Removed]"

fun ArticleResponse.toArticle() = Article(
    title = title,
    description = description ?: "",
    urlToImage = urlToImage ?: imageFallback,
    publishedAt = DateFormatterUtils.transformDate(publishedAt),
    author = author ?: "",
    active = title != removedTag,
)

