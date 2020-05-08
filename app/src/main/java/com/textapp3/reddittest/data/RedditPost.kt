package com.textapp3.reddittest.data

data class RedditPost(
    val key: String,
    val title: String,
    val score: Int,
    val author: String,
    val commentCount: Int,
    val image: String
)
