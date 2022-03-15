package com.example.newsfeedapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository
    private val data: MutableLiveData<List<News>> by lazy {
        MutableLiveData<List<News>>().also {
            loadNews(it)
        }
    }

    val news: LiveData<List<News>> = data

    fun updateData(callback: (() -> Unit)? = null) {
        loadNews(data, callback)
    }

    private fun loadNews(data: MutableLiveData<List<News>>, callback: (() -> Unit)? = null) {
        viewModelScope.launch {
            data.value = repository.getNews()
            callback?.invoke()
        }
    }
}