package com.example.newsfeedapplication.viewmodel

import android.app.Application
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.*
import com.example.newsfeedapplication.Converter
import com.example.newsfeedapplication.R
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import kotlinx.coroutines.launch

class DetailsViewModelFactory(private val application: Application, private val newsId: Int) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailsViewModel(application, newsId) as T
}

class DetailsViewModel(application: Application, private val newsId: Int) :
    AndroidViewModel(application) {
    private val repository = NewsRepository
    private val data: MutableLiveData<News> by lazy {
        MutableLiveData<News>().also { loadData(it) }
    }

    val news: LiveData<News> = data

    val description: Spanned = Html.fromHtml(news.value!!.description, Html.FROM_HTML_MODE_COMPACT)
    val creationTime = application.getString(
        R.string.published,
        Converter.formatInstant(news.value!!.creationTime)
    )
    val categories = Converter.getCategoriesString(news.value!!.categories)
    val content: Spanned = Html.fromHtml(news.value!!.content, Html.FROM_HTML_MODE_COMPACT)
    val link: Spanned = Html.fromHtml(
        application.getString(R.string.link_source, news.value!!.link),
        Html.FROM_HTML_MODE_COMPACT
    )

    private fun loadData(data: MutableLiveData<News>, callback: (() -> Unit)? = null) {
        viewModelScope.launch {
            data.value = repository.getByIndex(newsId)
            callback?.invoke()
        }
    }
}