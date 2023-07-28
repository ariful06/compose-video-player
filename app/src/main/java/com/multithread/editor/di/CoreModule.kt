package com.multithread.editor.di

import com.multithread.editor.utils.DefaultDispatcherProvider
import com.multithread.editor.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()
}