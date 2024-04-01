package com.giovannidiluca.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val title: String,
    val description: String,
    val urlToImage: String,
    val publishedAt: String,
    val author: String,
    val active: Boolean,
)