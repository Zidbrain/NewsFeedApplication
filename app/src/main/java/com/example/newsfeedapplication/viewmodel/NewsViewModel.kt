package com.example.newsfeedapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
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