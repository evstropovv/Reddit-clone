package com.textapp3.reddittest.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.textapp3.reddittest.data.RedditPost
import kotlinx.coroutines.CoroutineScope

interface PostsRepository {

    fun getPagedList(
        scope: CoroutineScope,
        errorListener: (Boolean) -> Unit
    ): LiveData<PagedList<RedditPost>>

    fun refresh(scope: CoroutineScope)

    fun retry()

}