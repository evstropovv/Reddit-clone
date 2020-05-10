package com.textapp3.reddittest.di

import android.app.Application
import androidx.room.Room
import com.textapp3.reddittest.data.db.PagingDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FoodiumDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(application, PagingDatabase::class.java, "redditposts.db")
            .build()

}