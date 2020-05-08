package com.textapp3.reddittest.data.network

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("/r/android/hot.json")
    suspend fun getPosts(
        @Query("limit") loadSize: Int = 30,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): RedditApiDto
}

data class RedditApiDto(val data: RedditListingDto) {

    data class RedditListingDto(val children: List<PostContainerDto>) {

        data class PostContainerDto(val data: RedditPostDto) {

            data class RedditPostDto(
                @SerializedName("name")
                val key: String,
                @SerializedName("title")
                val title: String,
                @SerializedName("score")
                val score: Int,
                @SerializedName("author")
                val author: String,
                @SerializedName("num_comments")
                val commentCount: Int,
                @SerializedName("thumbnail")
                val image: String
            )
        }
    }
}