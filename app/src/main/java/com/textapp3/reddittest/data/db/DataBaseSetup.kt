package com.textapp3.reddittest.data.db

import androidx.paging.DataSource
import androidx.room.*

@Database(entities = arrayOf(RedditPostEntity::class), version = 1)
abstract class PagingDatabase : RoomDatabase() {
    abstract fun postsDao(): RedditPostDao
}

@Dao
interface RedditPostDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts : List<RedditPostEntity>)

    @Query("SELECT * FROM RedditPostEntity")
    fun posts() : DataSource.Factory<Int, RedditPostEntity>

    @Query("DELETE FROM RedditPostEntity")
    suspend fun clearTable()
}
@Entity
data class RedditPostEntity(
    val key: String,
    @PrimaryKey
    val title: String,
    val score: Int,
    val author: String,
    val commentCount: Int,
    val image: String
)
