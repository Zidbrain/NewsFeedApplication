package com.example.newsfeedapplication.viewmodel

import android.text.Html
import android.text.Spanned
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.example.newsfeedapplication.Converter
import com.example.newsfeedapplication.R
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

class DetailsViewModel @AssistedInject constructor(
    private val repository: NewsRepository,
    @Assisted newsId: Int
) : ViewModel() {
    private var data: MutableLiveData<News> = MutableLiveData()

    var newsId: Int = newsId
        set(value) {
            field = value
            loadData(data)
        }

    @AssistedFactory
    fun interface DetailsViewModelFactory {
        fun create(newsId: Int): DetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DetailsViewModelFactory,
            newsId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(newsId) as T
        }
    }

    val news: LiveData<News> = data

    val description =
        news.map { Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT) }
    val creationTime = news.map {
        repository.getString(
            R.string.published,
            Converter.formatInstant(it.creationTime)
        )
    }
    val categories = news.map { Converter.getCategoriesString(it.categories) }
    val content = news.map { Html.fromHtml(it.content, Html.FROM_HTML_MODE_COMPACT) }
    val link = news.map {
        Html.fromHtml(
            repository.getString(R.string.link_source, it.link),
            Html.FROM_HTML_MODE_COMPACT
        )
    }

    private fun loadData(data: MutableLiveData<News>, callback: (() -> Unit)? = null) {
        viewModelScope.launch {
            data.value = repository.getByIndex(newsId)
            callback?.invoke()
        }
    }
}