package com.textapp3.reddittest.di

import com.textapp3.reddittest.data.PostsRepositoryImpl
import com.textapp3.reddittest.data.PostsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {

    @Binds
    abstract fun provideRepository(repo: PostsRepositoryImpl): PostsRepository
}
