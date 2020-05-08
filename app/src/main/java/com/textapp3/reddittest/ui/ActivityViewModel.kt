package com.textapp3.reddittest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.textapp3.reddittest.data.Mapper
import com.textapp3.reddittest.data.RedditPost
import com.textapp3.reddittest.data.db.PagingDatabase
import com.textapp3.reddittest.data.network.RedditService
import com.textapp3.reddittest.ui.paging.BoundaryCallback
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val db: PagingDatabase,
    api: RedditService,
    private val mapper: Mapper
) : ViewModel() {

    private val retryMutableLiveData = MutableLiveData<Boolean>(false)
    val retryLiveData: LiveData<Boolean> = retryMutableLiveData

    private val boundaryCallback =
        BoundaryCallback(viewModelScope, db.postsDao(), api, mapper) {
            retryMutableLiveData.postValue(it)
        }

    val pagedList: LiveData<PagedList<RedditPost>> by lazy {
        LivePagedListBuilder<Int, RedditPost>(
            db.postsDao().posts().map { mapper.toDomain(it) }, 100
        ).setBoundaryCallback(
            boundaryCallback
        ).build()
    }

    fun retry() {
        retryMutableLiveData.value = false
        boundaryCallback.retry?.invoke()
    }

    fun refresh() {
        viewModelScope.launch {
            db.postsDao().clearTable()
        }
    }

}
