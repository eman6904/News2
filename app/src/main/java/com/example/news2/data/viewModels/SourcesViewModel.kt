package com.example.news2.data.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news2.MainActivity.Companion.SELECTED_LANGUAGE
import com.example.news2.MainActivity.Companion.appLanguage
import com.example.news2.R
import com.example.news2.data.apis.ApiConnection
import com.example.news2.data.models.Source
import com.example.news2.data.models.SourcesResponse
import com.example.news2.data.uiState.UIState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class SourcesViewModel:ViewModel() {

    private val _sourcesResponse = MutableLiveData<UIState<SourcesResponse>?>(null)
    val sourcesResponse : LiveData<UIState<SourcesResponse>?> get() = _sourcesResponse

    private val _category = MutableLiveData<String>()
    val category : LiveData<String> get() = _category

    private val _sourcesList = MutableLiveData<List<Source>>(emptyList())
    val sourcesList : LiveData<List<Source>> get() = _sourcesList

    private val _sourceId = MutableLiveData<String>()
    val sourceId : LiveData<String> get() = _sourceId

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery : LiveData<String> get() = _searchQuery

    val combinedLiveData = MediatorLiveData<Pair<String, String>>()

    private val _currentFragment = MutableLiveData<Int>(R.id.nav_categories)
    val currentFragment : LiveData<Int> get() = _currentFragment

    init{
        combinedLiveData.addSource(sourceId) { sourceId ->
            val query = searchQuery.value ?: ""
            combinedLiveData.value = Pair(sourceId, query)
        }

        combinedLiveData.addSource(searchQuery) { searchQuery ->
            val sourceId = sourceId.value ?: ""
            combinedLiveData.value = Pair(sourceId, searchQuery)
        }
    }
    fun setCurrentFragment(fragmentId:Int){

        _currentFragment.value = fragmentId

    }

    fun setSearchQuery(searchQuery:String){

        _searchQuery.value=searchQuery
    }
    fun setCategory(categoryName:String){

        _category.value = categoryName
    }

    fun setSource(sourceId:String){

        _sourceId.value = sourceId
    }
    fun removeResponse(){
        _sourcesResponse.value = null
    }

    fun setSources(sources:List<Source>){

        _sourcesList.value=sources
    }


    fun getSources(
        categoryName:String,
        context: Context
    ){
        _sourcesResponse.value = UIState.Loading
        ApiConnection().getSources(
            categoryName = categoryName,
            language = appLanguage.getString(SELECTED_LANGUAGE, null)?:"en"
        ).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {

                    response.body()?.let { sources ->
                        _sourcesResponse.value = UIState.Success(sources)
                    } ?: run {
                        _sourcesResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))
                    }
                } else {
                    _sourcesResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                when(t){
                    is IOException ->{ _sourcesResponse.value = UIState.Error(context.getString(R.string.check_your_internet_connection))}

                    is HttpException ->{ _sourcesResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))}

                    is Exception ->{_sourcesResponse.value = UIState.Error(context.getString(R.string.something_went_wrong))}
                }
            }
        })
    }
}