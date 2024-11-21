package com.example.news2.data.apis

import com.example.news2.data.models.NewsResponse
import com.example.news2.data.models.SourcesResponse
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

class ApiConnection {
    val postInterface: ApiServices

    constructor () {
        val retrofit = Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        postInterface = retrofit.create(ApiServices::class.java)
    }

    fun getSources(categoryName:String,language: String): retrofit2.Call<SourcesResponse> {
        return postInterface.getSources(
            category = categoryName,
            apiKey = "d345bd7c207246288e20202fcd1aab03",
            language =  language
        )
    }
    fun getNews(sourceId:String,searchQuery:String): retrofit2.Call<NewsResponse> {
        return postInterface.getNews(
            sourceId = sourceId,
            apiKey = "d345bd7c207246288e20202fcd1aab03",
            searchQuery = searchQuery
        )
    }

}