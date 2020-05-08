package com.textapp3.reddittest.data

import com.textapp3.reddittest.data.db.RedditPostEntity
import com.textapp3.reddittest.data.network.RedditApiDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mapper @Inject constructor() {
    fun toDomain(post: RedditPostEntity): RedditPost =
        RedditPost(
            key = post.key,
            title = post.title,
            score = post.score,
            author = post.author,
            commentCount = post.commentCount,
            image = post.image
        )

    fun toDomain(post: RedditApiDto.RedditListingDto.PostContainerDto.RedditPostDto): RedditPost =
        RedditPost(
            key = post.key,
            title = post.title,
            score = post.score,
            author = post.author,
            commentCount = post.commentCount,
            image = post.image
        )

    fun toEntity(post: RedditApiDto.RedditListingDto.PostContainerDto.RedditPostDto): RedditPostEntity =
        RedditPostEntity(
            key = post.key,
            title = post.title,
            score = post.score,
            author = post.author,
            commentCount = post.commentCount,
            image = post.image
        )
}