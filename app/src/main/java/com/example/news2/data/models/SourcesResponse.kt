package com.example.news2.data.models

data class SourcesResponse(
    val status: String? = null,
    val sources: List<Source> = emptyList()
)
