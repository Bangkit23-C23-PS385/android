package com.bangkitacademy.medicare.ui.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkitacademy.medicare.data.remote.response.ArticlesItem
import com.bangkitacademy.medicare.repository.NewsRepository

class BerandaViewModel(
    newsRepository: NewsRepository
) : ViewModel() {

    val news: LiveData<PagingData<ArticlesItem>> = newsRepository.getNews().cachedIn(viewModelScope)

}