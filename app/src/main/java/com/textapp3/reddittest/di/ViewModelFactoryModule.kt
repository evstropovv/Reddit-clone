package com.textapp3.reddittest.di

import androidx.lifecycle.ViewModelProvider
import com.textapp3.reddittest.ui.ViewModelProviderFactory
import dagger.Binds
import dagger.Module


@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}