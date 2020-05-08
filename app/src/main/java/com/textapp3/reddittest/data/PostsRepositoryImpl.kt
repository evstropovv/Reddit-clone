package com.textapp3.reddittest.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.textapp3.reddittest.data.db.PagingDatabase
import com.textapp3.reddittest.data.network.RedditService
import com.textapp3.reddittest.data.paging.BoundaryCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val db: PagingDatabase,
    private val api: RedditService,
    private val mapper: Mapper
) : PostsRepository {

    private var boundaryCallback: BoundaryCallback? = null

    override fun getPagedList(
        scope: CoroutineScope,
        errorListener: (Boolean) -> Unit
    ): LiveData<PagedList<RedditPost>> {

        boundaryCallback = getBoundaryCallback(scope, errorListener)

        return LivePagedListBuilder<Int, RedditPost>(db.postsDao().posts()
            .map { mapper.toDomain(it) }, 50
        ).setBoundaryCallback(boundaryCallback).build()
    }


    override fun refresh(scope: CoroutineScope) {
        scope.launch {
            db.postsDao().clearTable()
        }
    }

    override fun retry() {
        boundaryCallback?.retry?.invoke()
    }

    private fun getBoundaryCallback(
        scope: CoroutineScope,
        errorListener: (Boolean) -> Unit
    ): BoundaryCallback {
        return BoundaryCallback(
            scope,
            db.postsDao(),
            api,
            mapper,
            errorListener
        )
    }

}