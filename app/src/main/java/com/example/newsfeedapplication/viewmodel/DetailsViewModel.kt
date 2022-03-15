package com.example.newsfeedapplication.viewmodel

import android.text.Html
import android.text.Spanned
import androidx.lifecycle.*
import com.example.newsfeedapplication.Converter
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import kotlinx.coroutines.launch

class DetailsViewModelFactory(private val newsId: Int): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T  =
        DetailsViewModel(newsId) as T
}

class DetailsViewModel(private val newsId: Int) : ViewModel() {
    private val repository = NewsRepository
    private val data: MutableLiveData<News> by lazy {
        MutableLiveData<News>().also { loadData(it) }
    }

    val news: LiveData<News> = data
    
    val description: Spanned = Html.fromHtml(news.value!!.description, Html.FROM_HTML_MODE_COMPACT)
    val creationTime = "Published: " + Converter.formatInstant(news.value!!.creationTime)
    val categories = Converter.getCategoriesString(news.value!!.categories)
    val content: Spanned = Html.fromHtml(news.value!!.content, Html.FROM_HTML_MODE_COMPACT)
    val link: Spanned = Html.fromHtml(news.value!!.link.run { "Source: <a href=\"$this\">$this</a>" }, Html.FROM_HTML_MODE_COMPACT)

    private fun loadData(data: MutableLiveData<News>, callback: (() -> Unit)? = null){
        viewModelScope.launch { 
            data.value = repository.getByIndex(newsId)
            callback?.invoke()
        }
    }
}