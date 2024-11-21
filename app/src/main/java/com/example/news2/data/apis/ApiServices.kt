package com.example.news2.data.apis

import com.example.news2.data.models.NewsResponse
import com.example.news2.data.models.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices  {
    @GET("sources")
    fun getSources(@Query("category")category:String, @Query("apiKey")apiKey:String, @Query("language")language:String):retrofit2.Call<SourcesResponse>
    @GET("everything")
    fun getNews(@Query("sources")sourceId:String?,@Query("apiKey")apiKey:String,@Query("q")searchQuery:String):retrofit2.Call<NewsResponse>
}