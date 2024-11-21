package com.example.news2.data.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Post> = emptyList()
)
