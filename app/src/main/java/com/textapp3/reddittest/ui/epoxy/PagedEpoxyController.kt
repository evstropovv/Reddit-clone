package com.textapp3.reddittest.ui.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.textapp3.reddittest.data.RedditPost

class PagedEpoxyController : PagedListEpoxyController<RedditPost>() {

    override fun buildItemModel(currentPosition: Int, item: RedditPost?): EpoxyModel<*> {
        return if (item == null) {
            PagingViewModel_()
                .id(-currentPosition)
                .name("loading $currentPosition")
        } else {
            PostItemView_()
                .id(item.key)
                .author(item.author)
                .title(item.title)
                .imageUrl(item.image)
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        pagingView {
            id("header")
            name("showing ${models.size} items")
        }
        super.addModels(models)
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}