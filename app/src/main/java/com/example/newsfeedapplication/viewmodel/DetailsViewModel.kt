package com.example.newsfeedapplication.viewmodel

import androidx.lifecycle.*
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.model.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

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

    private fun loadData(data: MutableLiveData<News>, callback: (() -> Unit)? = null) {
        viewModelScope.launch {
            data.value = repository.getByIndex(newsId)
            callback?.invoke()
        }
    }
}