package com.textapp3.reddittest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.textapp3.reddittest.data.PostsRepository
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
   private val postsRepository: PostsRepository
) : ViewModel() {

    private val retryMutableLiveData = MutableLiveData<Boolean>(false)
    val retryLiveData: LiveData<Boolean> = retryMutableLiveData

    val pagedList = postsRepository.getPagedList(viewModelScope) {
        retryMutableLiveData.postValue(it)
    }

    fun retry() {
        retryMutableLiveData.value = false
        postsRepository.retry()
    }

    fun refresh() {
        postsRepository.refresh(viewModelScope)
    }

}
