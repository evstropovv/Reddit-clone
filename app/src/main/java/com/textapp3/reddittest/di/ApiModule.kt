package com.textapp3.reddittest.di

import com.textapp3.reddittest.data.network.RedditService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
public class ApiModule {

    @Singleton
    @Provides
    fun provideRedditService(): RedditService {
        return Retrofit.Builder()
            .baseUrl("https://reddit.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RedditService::class.java)
    }
}
