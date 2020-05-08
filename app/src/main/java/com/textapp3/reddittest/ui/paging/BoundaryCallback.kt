package com.textapp3.reddittest.ui.paging

import androidx.paging.PagedList
import com.textapp3.reddittest.data.Mapper
import com.textapp3.reddittest.data.RedditPost
import com.textapp3.reddittest.data.db.RedditPostDao
import com.textapp3.reddittest.data.db.RedditPostEntity
import com.textapp3.reddittest.data.network.RedditService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class BoundaryCallback @Inject constructor(
    private val scope: CoroutineScope,
    private val db: RedditPostDao,
    private val api: RedditService,
    private val mapper: Mapper,
    private val errorListener: (Boolean) -> Unit
) : PagedList.BoundaryCallback<RedditPost>() {

    var retry: (() -> Any)? = null

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        scope.launch(Dispatchers.IO) {
            val posts = try {
                api.getPosts().data.children.map { mapper.toEntity(it.data) }
            } catch (e: Exception) {
                errorListener.invoke(true)
                retry = {
                    onZeroItemsLoaded()
                }
                ArrayList<RedditPostEntity>()
            }
            db.insert(posts)
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: RedditPost) {
        super.onItemAtEndLoaded(itemAtEnd)
        scope.launch(Dispatchers.IO) {
            val posts = try {
                retry = null
                api.getPosts(after = itemAtEnd.key).data.children.map { mapper.toEntity(it.data) }

            } catch (e: Exception) {
                errorListener.invoke(true)
                retry = {
                    onItemAtEndLoaded(itemAtEnd)
                }
                ArrayList<RedditPostEntity>()
            }
            db.insert(posts)
        }
    }
}