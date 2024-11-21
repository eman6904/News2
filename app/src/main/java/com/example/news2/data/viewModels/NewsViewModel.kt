package com.example.news2.data.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news2.R
import com.example.news2.data.apis.ApiConnection
import com.example.news2.data.models.NewsResponse
import com.example.news2.data.models.Post
import com.example.news2.data.uiState.UIState
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import java.io.IOException

class NewsViewModel:ViewModel(){

    private val _newsResponse = MutableLiveData<UIState<NewsResponse>?>(null)
    val newsResponse : LiveData<UIState<NewsResponse>?> get() = _newsResponse

    private val _newsList = MutableLiveData<List<Post>>(emptyList())
    val newsList : LiveData<List<Post>> get() = _newsList

    fun clearNews(){

        _newsResponse.value = UIState.Loading
        _newsList.value = emptyList()
    }
    fun setNews(newsList:List<Post>){

        _newsList.value = newsList
    }
    fun getNews(
        sourceId:String,
        searchQuery:String,
        context: Context
    ){
        _newsResponse.value = UIState.Loading
        ApiConnection().getNews(
            sourceId = sourceId,
            searchQuery = searchQuery
        ).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {

                    response.body()?.let { sources ->
                        _newsResponse.value = UIState.Success(sources)
                    } ?: run {
                        _newsResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))
                    }
                } else {
                    _newsResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                when(t){
                    is IOException ->{ _newsResponse.value = UIState.Error(context.getString(R.string.check_your_internet_connection))}

                    is HttpException ->{ _newsResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))}

                    is Exception ->{_newsResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))}
                }
            }
        })
    }
}
